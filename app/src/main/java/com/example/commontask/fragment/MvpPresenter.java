package com.example.commontask.fragment;

public interface MvpPresenter<V extends MvpView> {
    void attachView(V view);

    void detachView();

    boolean isViewAttached();

}