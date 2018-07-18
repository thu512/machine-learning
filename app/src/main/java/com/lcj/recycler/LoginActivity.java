package com.lcj.recycler;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;


public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {


    // google
    private FirebaseAuth.AuthStateListener mAuthListener;

    // google
    private static final int RC_SIGN_IN = 9001; // 구글 로그인 요청코드 (값은 변경 가능함)
    private FirebaseAuth mAuth;                 // fb 인증 객체
    private GoogleApiClient mGoogleApiClient;   // 구글 로그인 담당 객체(Api 담당 객체)



    SignInButton btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        //getHash();
        // google
        initGoogleLoginInit();

        //google버튼모양
        btn = (SignInButton)findViewById(R.id.sign_in_button);
        btn.setSize(SignInButton.SIZE_WIDE);

        //구글 로그인
        btn.setOnClickListener((view -> {
            onGoogleLogin();
        }));

        //구글
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("INFO","onAuthStateChanged:signed_in:" + user.getUid());

                } else {
                    // User is signed out
                    Log.d("INFO","onAuthStateChanged:signed_out");
                }
            }
        };
    }

    // google
    // 초기화
    public void initGoogleLoginInit() {

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Log.d("ERROR","구글 로그인 실패 메세지: " + connectionResult.getErrorMessage());

                    }
                } /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        mAuth = FirebaseAuth.getInstance();

    }


    // google
    public void onGoogleLogin() {
        signIn();
    }


    // google
    // 구글 로그인 시작점
    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        // 다른 액티비티를 구동해서 결과를 돌려 받을려면 이렇게 액티비티를 구동해야 한다.
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    // google
    // 로그인 성공후 호출 코드
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d("INFO","firebaseAuthWithGoogle:" + acct.getId());
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, (task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        FirebaseUser user = mAuth.getCurrentUser();
                        Log.d("USER","" + user.getDisplayName());
                        Log.d("USER","" + user.getEmail());
                        Log.d("USER","" + user.getUid());
                        Log.d("USER","" + user.getPhotoUrl().toString());

                        startActivity(new Intent(getApplicationContext(), MenuActivity.class));
                        finish();

                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(getApplicationContext(),"" + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }));
    }

    @Override
    public void onStart() {
        super.onStart();
        //google
        mAuth.addAuthStateListener(mAuthListener);

        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            Log.d("USER","" + user.getDisplayName());
            Log.d("USER","" + user.getEmail());
            Log.d("USER","" + user.getUid());
            //U.log("" + user.getPhotoUrl().toString());
            if (user.isEmailVerified()) {
                startActivity(new Intent(getApplicationContext(), MenuActivity.class));
                finish();
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        //google
        //fb인증관련 리스너 해제
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // google
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                Log.d("ERROR","google 로그인 실패\n" + result.getStatus().getStatusMessage()+"\n"+"" + result.getStatus().toString());
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

        Log.d("ERROR","연결 실패 => 재시도 같은 시나리오 필요:" + connectionResult);
    }


}
