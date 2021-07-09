package com.example.nikestore.view.Fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.nikestore.R
import com.example.nikestore.common.NikeFragment
import com.example.nikestore.viewmodel.AuthViewModel
import com.sevenlearn.nikestore.common.NikeCompletableObserver
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.CompletableObserver
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_sign_up.emailEt
import kotlinx.android.synthetic.main.fragment_sign_up.loginBtn
import kotlinx.android.synthetic.main.fragment_sign_up.passwordEt
import kotlinx.android.synthetic.main.fragment_sign_up.*
import org.jetbrains.annotations.Async
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel


class SignUpFragment : NikeFragment() {


    val authViewModel: AuthViewModel by viewModel()
    val compositeDisposable: CompositeDisposable by inject()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        signUp()
        onLoginClickListener()
    }

    fun signUp(){

        signUpBtn.setOnClickListener {
            Log.i("dgsdfg",emailEt.text.toString()+passwordEt.text.toString())
            authViewModel.signUp(emailEt.text.toString(),passwordEt.text.toString())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object:NikeCompletableObserver(compositeDisposable){
                        override fun onComplete() {
                            requireActivity().finish()
                            Toast.makeText(requireContext(),
                                    getString(R.string.yourAccountSuccessfullyCreated),
                                    Toast.LENGTH_SHORT).show()
                        }

                    })

        }



    }

    fun onLoginClickListener(){

        loginBtn.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragmentContainer, LoginFragment())
            }.commit()
        }
    }

    override fun onStop() {
        compositeDisposable.clear()
        super.onStop()
    }

}