package com.example.nt_project02;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // 초기화 Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.CheckButton).setOnClickListener(onClickListener);
        findViewById(R.id.gotoPasswordResetbutton).setOnClickListener(onClickListener);
        findViewById(R.id.signUp_Activity_Button).setOnClickListener(onClickListener);
        findViewById(R.id.activity_login_TemporaryNativeButton).setOnClickListener(onClickListener);
        findViewById(R.id.activity_login_TemporaryTravelerButton).setOnClickListener(onClickListener);
    }

    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }


    View.OnClickListener onClickListener =   new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.CheckButton:
                    login();
                    break;

                case R.id.gotoPasswordResetbutton:
                    MystartActivity(PasswordResetActivity.class);
                    break;

                case R.id.signUp_Activity_Button:
                    MystartActivity(Sign_UpActivity.class);
                    break;
                case R.id.activity_login_TemporaryNativeButton:
                    Temporary_native_login();
                    break;

                case R.id.activity_login_TemporaryTravelerButton:
                    Temporary_traveler_login();
                    break;

            }


        }


    };

    private void login() {

        String email = ((EditText) findViewById(R.id.NameEditText)).getText().toString();
        String password = ((EditText) findViewById(R.id.passwordEditText)).getText().toString();


        if (email.length() > 0 && password.length() > 0) {

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                FirebaseUser user = mAuth.getCurrentUser();
                                startToast("로그인 성공");
                                String uid=user.getUid();
                                // 파이어스토어 객체선언
                                FirebaseFirestore db = FirebaseFirestore.getInstance();
                                //파이어스토어에서 해당 유저의 uid를 이용하여 정보 가져오기
                                final DocumentReference docRef = db.collection("users").document(uid);
                                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    //정보 가져오는 것이 성공적일 때
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {

                                            //DocumentSnapshot에 정보를 담아둠

                                            DocumentSnapshot document = task.getResult();

                                            //document가 null이 아닐 때

                                            if(document!=null){

                                                //재차 확인
                                                if (document.exists()) {


                                                    MystartActivity(MainActivity.class);
                                                } else {

//                                                    //로그인은 됐는데, 상세정보가 등록되어 있지 않으면 MemberActivity클래스로 이동
                                                    MystartActivity(MemberActivity.class);
                                                }
                                            }
                                            //아예 오류떠서 실패했을 때
                                        } else {
                                            Log.d("login", "get failed with ", task.getException());
                                        }
                                    }
                                });



                            } else {

                                if (task.getException() != null) {
                                    startToast(task.getException().toString());
                                }
                            }

                            // ...
                        }
                    });

        } else {


            startToast("이메일 또는 비밀번호를 입력해주세요.");
        }


    }

    private void Temporary_native_login() {

        String email = "native@naver.com";
        String password = "123456";


        if (email.length() > 0 && password.length() > 0) {

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                FirebaseUser user = mAuth.getCurrentUser();
                                startToast("로그인 성공");
                                String uid=user.getUid();
                                // 파이어스토어 객체선언
                                FirebaseFirestore db = FirebaseFirestore.getInstance();
                                //파이어스토어에서 해당 유저의 uid를 이용하여 정보 가져오기
                                final DocumentReference docRef = db.collection("users").document(uid);
                                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    //정보 가져오는 것이 성공적일 때
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {

                                            //DocumentSnapshot에 정보를 담아둠

                                            DocumentSnapshot document = task.getResult();

                                            //document가 null이 아닐 때

                                            if(document!=null){

                                                //재차 확인
                                                if (document.exists()) {


                                                    MystartActivity(MainActivity.class);
                                                } else {

                                                    //로그인은 됐는데, 상세정보가 등록되어 있지 않으면 MemberActivity클래스로 이동
                                                    MystartActivity(MemberTypeActivity.class);
                                                }
                                            }
                                            //아예 오류떠서 실패했을 때
                                        } else {
                                            Log.d("login", "get failed with ", task.getException());
                                        }
                                    }
                                });



                            } else {

                                if (task.getException() != null) {
                                    startToast(task.getException().toString());
                                }
                            }

                            // ...
                        }
                    });

        } else {


            startToast("이메일 또는 비밀번호를 입력해주세요.");
        }


    }

    private void Temporary_traveler_login() {

        String email = "traveler@naver.com";
        String password = "123456";


        if (email.length() > 0 && password.length() > 0) {

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                FirebaseUser user = mAuth.getCurrentUser();
                                startToast("로그인 성공");
                                String uid=user.getUid();
                                // 파이어스토어 객체선언
                                FirebaseFirestore db = FirebaseFirestore.getInstance();
                                //파이어스토어에서 해당 유저의 uid를 이용하여 정보 가져오기
                                final DocumentReference docRef = db.collection("users").document(uid);
                                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    //정보 가져오는 것이 성공적일 때
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {

                                            //DocumentSnapshot에 정보를 담아둠

                                            DocumentSnapshot document = task.getResult();

                                            //document가 null이 아닐 때

                                            if(document!=null){

                                                //재차 확인
                                                if (document.exists()) {


                                                    MystartActivity(MainActivity.class);
                                                } else {

                                                    //로그인은 됐는데, 상세정보가 등록되어 있지 않으면 MemberActivity클래스로 이동
                                                    MystartActivity(MemberTypeActivity.class);
                                                }
                                            }
                                            //아예 오류떠서 실패했을 때
                                        } else {
                                            Log.d("login", "get failed with ", task.getException());
                                        }
                                    }
                                });



                            } else {

                                if (task.getException() != null) {
                                    startToast(task.getException().toString());
                                }
                            }

                            // ...
                        }
                    });

        } else {


            startToast("이메일 또는 비밀번호를 입력해주세요.");
        }


    }






    private void startToast(String msg){

        Toast.makeText(LoginActivity.this, msg,
                Toast.LENGTH_SHORT).show();
    }
    private void MystartActivity(Class c){
        Intent intent=new Intent(this,c);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }
}
