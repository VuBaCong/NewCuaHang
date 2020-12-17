package com.example.cuahangonline.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.cuahangonline.R;
import com.example.cuahangonline.activity.LoginActivity;
import com.example.cuahangonline.model.KhachHang;
import com.example.cuahangonline.utils.checkconnection;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterFragment extends Fragment {

    private EditText edtUserName;
    private EditText edtPassWord;
    private EditText edtEmail;
    private Button btnRegister;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    private Boolean validateUsername() {
        String val = edtUserName.getText().toString();
        String noWhiteSpace = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$"; // tối thiểu 6 ký tự, ít nhất 1 chữ cái và 1 số

        if (val.isEmpty()) {
            LoginActivity.prefConfig.displayToast(getResources().getString(R.string.username_empty));
            return false;
        } else if (val.length()<= 6) {
            LoginActivity.prefConfig.displayToast(getResources().getString(R.string.username_short));
            return false;
        } else if (val.length() >= 15) {
            LoginActivity.prefConfig.displayToast(getResources().getString(R.string.username_long));
            return false;
        } else if (!val.matches(noWhiteSpace)) {
            LoginActivity.prefConfig.displayToast(getResources().getString(R.string.username_nowhitespace));
            return false;
        } else {
            edtUserName.setError(null);
            edtUserName.setEnabled(false);
            return true;
        }
    }

    private Boolean validatePassword() {
        String val = edtPassWord.getText().toString();
        String passwordVal = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$"; // tối thiểu 8 ký tự, ít nhất 1 chữ hoa, 1 chữ thường và 1 số

        if (val.isEmpty()) {
            LoginActivity.prefConfig.displayToast(getResources().getString(R.string.password_empty));
            return false;
        } else if (val.length() <= 8){
            LoginActivity.prefConfig.displayToast(getResources().getString(R.string.password_short));
            return false;
        }else if (!val.matches(passwordVal)) {
            LoginActivity.prefConfig.displayToast(getResources().getString(R.string.password_weak));
            return false;
        } else {
            edtPassWord.setError(null);
            edtPassWord.setEnabled(false);
            return true;
        }
    }

    private Boolean validateEmail() {
        String val = edtEmail.getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()) {
            LoginActivity.prefConfig.displayToast(getResources().getString(R.string.email_empty));
            return false;
        } else if (!val.matches(emailPattern)) {
            LoginActivity.prefConfig.displayToast(getResources().getString(R.string.email_invalid));
            return false;
        } else {
            edtEmail.setEnabled(false);
            return true;
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Anhxa(view);
        setEvent();
    }

    private void setEvent() {
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!edtUserName.getText().toString().equals("")&&!edtPassWord.getText().toString().equals("")&&!edtEmail.getText().toString().equals("") ){

                    if (!validateUsername()|!validatePassword()|!validateEmail()) {

                    }else {
                        if (checkconnection.haveNetworkConnection(getContext())){
                            performRegister();
                        }else {
                            LoginActivity.prefConfig.displayToast(getString(R.string.no_network));
                        }
                    }

                }else {
                    LoginActivity.prefConfig.displayToast(getResources().getString(R.string.login_empty));
                }
            }
        });
    }

    private void performRegister(){
        String image= "default";
        String email=edtEmail.getText().toString();
        String username=edtUserName.getText().toString();
        String password=edtPassWord.getText().toString();
        Call<KhachHang> call= LoginActivity.apiInterface.performRegistrantion(email,username,password,image);
        final ProgressDialog progressDialog=new ProgressDialog(getContext());
        progressDialog.setMessage("Đang đăng ký...");
        progressDialog.show();
        call.enqueue(new Callback<KhachHang>() {
            @Override
            public void onResponse(Call<KhachHang> call, Response<KhachHang> response) {
                progressDialog.dismiss();
                KhachHang user=response.body();
                if (!response.isSuccessful()){
                    return;
                }
                if (user==null) return;
                if (user.getResponse().equals("ok")){
                    LoginActivity.prefConfig.displayToast(getString(R.string.register_success));
                    getActivity().getSupportFragmentManager().popBackStack();
                }
                else if (user.getResponse().equals("exist")){
                    LoginActivity.prefConfig.displayToast(getString(R.string.register_exits));
                }
                else if (user.getResponse().equals("error")){
                    LoginActivity.prefConfig.displayToast(getString(R.string.register_error));
                }
            }

            @Override
            public void onFailure(Call<KhachHang> call, Throwable t) {
                progressDialog.dismiss();
                LoginActivity.prefConfig.displayToast(t.getMessage());
            }
        });

    }

    private void Anhxa(View view) {
        edtUserName=view.findViewById(R.id.edtUsername);
        edtEmail=view.findViewById(R.id.edtEmail);
        edtPassWord=view.findViewById(R.id.edtPassWord);
        btnRegister=view.findViewById(R.id.btnRegister);
    }
}
