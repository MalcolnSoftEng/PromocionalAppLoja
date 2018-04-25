/*
 * Copyright (c) $today.year.SpheraSystems I.T - 2017
 */

package syte.femininapv3;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import syte.femininapv3.model.PromocoesItem;
import syte.femininapv3.webservicePromocoes.PromocoesHttp;
import syte.femininapv3.webservicePromocoes.PromocoesListAdapter;

/**
 * Created by Malcoln - SpheraSystems I.T
 */
public class PromocoesListWebServiceFragment extends Fragment implements PromocoesListAdapter.AoClicarNaPromocaoListener{

    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout mSwipe;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    Unbinder unbinder;

    private PromocoesItem[] mPromocoesItem;
    PromocoesDownloadTask mTask;




    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.promocoes_coordinator_layout,container,false);

        ButterKnife.bind(this,view);
        mSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mTask = new PromocoesDownloadTask();
                mTask.execute();
            }
        });

        mRecyclerView.setTag("web");
        mRecyclerView.setHasFixedSize(true);
        //if (getResources().getConfiguration().orientation== Configuration.ORIENTATION_PORTRAIT){
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //} else {
        //    mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),1));
       // }
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //chamar unbind da view
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mPromocoesItem == null){
            if (mTask == null){
                mTask = new PromocoesDownloadTask();
                mTask.execute();
            } else if (mTask.getStatus() == AsyncTask.Status.RUNNING){
                exibirProgresso();
            }
        } else {
            atualizarLista();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mTask!=null)mTask.cancel(true);
    }

    @Override
    public void aoClicarNoItemPromocoes(View view, int position, PromocoesItem promocoesItem) {
        Intent it = new Intent(getActivity(), PromocoesItemDetalhesAct.class);
        it.putExtra(PromocoesItemDetalhesAct.EXTRA_ITEM, promocoesItem);
        startActivity(it);
    }

    private void atualizarLista() {
        PromocoesListAdapter adapter = new PromocoesListAdapter(getActivity(), mPromocoesItem);
        adapter.setAoCLicarNaPromocaoListener(this);
        mRecyclerView.setAdapter(adapter);
    }

    public void exibirProgresso(){
        mSwipe.post(new Runnable() {
            @Override
            public void run() {
                mSwipe.setRefreshing(true);
            }
        });
    }

    private class PromocoesDownloadTask extends AsyncTask<Void, Void,PromocoesItem[]>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            exibirProgresso();
        }

        @Override
        protected PromocoesItem[] doInBackground(Void... voids) {

            return PromocoesHttp.obterNovidadesDoServidor();
        }

        @Override
        protected void onPostExecute(PromocoesItem[] novidadesItems) {
            super.onPostExecute(novidadesItems);
            mSwipe.setRefreshing(false);
            if (novidadesItems!= null){
                mPromocoesItem = novidadesItems;
                atualizarLista();
            }
        }
    }
}
