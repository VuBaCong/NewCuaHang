package com.example.cuahangonline.fragment;

import android.app.KeyguardManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.cuahangonline.R;
import com.example.cuahangonline.activity.LoginActivity;
import com.example.cuahangonline.activity.MainActivity;
import com.example.cuahangonline.model.KhachHang;
import com.example.cuahangonline.utils.checkconnection;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment {

    private EditText edtUsername;
    private EditText edtPassword;
    private Button btnLogin;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edtUsername=view.findViewById(R.id.edtUsername);
        edtPassword=view.findViewById(R.id.edtPassWord);
        btnLogin=view.findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!edtUsername.getText().toString().equals("")&&!edtPassword.getText().toString().equals("") ){
                    if (checkconnection.haveNetworkConnection(getContext())){
                        performLogin();
                    }else {
                        LoginActivity.prefConfig.displayToast(getString(R.string.no_network));
                    }

                }else {
                    LoginActivity.prefConfig.displayToast(getResources().getString(R.string.login_empty));
                }
            }
        });
    }

    private void performLogin(){
        final String username=edtUsername.getText().toString();
        final String password=edtPassword.getText().toString();
        Call<KhachHang> call= LoginActivity.apiInterface.performUserLogin(username,password);
        final ProgressDialog progressDialog=new ProgressDialog(getContext());
        progressDialog.setMessage("Đang đăng nhập...");
        progressDialog.show();
        call.enqueue(new Callback<KhachHang>() {
            @Override
            public void onResponse(Call<KhachHang> call, Response<KhachHang> response) {
                progressDialog.dismiss();
                if (!response.isSuccessful()){
                    return;
                }
                KhachHang user=response.body();
                if (user==null){
                    return;
                }
                if (user.getResponse().equals("ok")){
                    LoginActivity.prefConfig.writeIdUser(user.getId());
                    LoginActivity.prefConfig.writeLoginStatus(true);
                    LoginActivity.prefConfig.writeEmail(user.getEmail());
                    LoginActivity.prefConfig.writeUserName(username);
                    LoginActivity.prefConfig.writePassWord(password);
                    LoginActivity.prefConfig.writeLinkImage(user.getImage());
                    LoginActivity.prefConfig.writeCheckAdmin(user.getCheckAdmin());
                    if (user.getCheckAdmin()==0){
                        Intent intent=new Intent(getContext(), MainActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    }else {
/*                        Intent intent=new Intent(getContext(), AdminManagerAcitivity.class);
                        startActivity(intent);
                        getActivity().finish();*/
                    }
                }
                else if (user.getResponse().equals("failed")){
                    LoginActivity.prefConfig.displayToast(getResources().getString(R.string.login_fail));
                }
            }

            @Override
            public void onFailure(Call<KhachHang> call, Throwable t) {
                progressDialog.dismiss();
                LoginActivity.prefConfig.displayToast(getResources().getString(R.string.onfailure_login));
            }
        });

    }
}
