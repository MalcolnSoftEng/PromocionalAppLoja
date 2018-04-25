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
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.Unbinder;
import syte.femininapv3.model.NovidadesItem;
import syte.femininapv3.webserviceNovidades.NovidadesListAdapter;
import syte.femininapv3.webserviceNovidades.NovidadesHttp;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Malcoln - SpheraSystems I.T
 */
public class NovidadesListWebServiceFragment extends Fragment implements NovidadesListAdapter.AoClicarNaPromocaoListener{

    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout mSwipe;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    Unbinder unbinder;

    private NovidadesItem[] mNovidadesItem;
    NovidadesDownloadTask mTask;




    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.coordinator_layout,container,false);

        ButterKnife.bind(this,view);
        mSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mTask = new NovidadesDownloadTask();
                mTask.execute();
            }
        });

        mRecyclerView.setTag("web");
        mRecyclerView.setHasFixedSize(true);
        //if (getResources().getConfiguration().orientation== Configuration.ORIENTATION_PORTRAIT){
        //    mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //} else {
            mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));
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
        if (mNovidadesItem == null){
            if (mTask == null){
                mTask = new NovidadesDownloadTask();
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
    public void aoClicarNoItemNovidades(View view, int position, NovidadesItem novidadesItem) {
        Intent it = new Intent(getActivity(), NovidadesItemDetalhesAct.class);
        it.putExtra(NovidadesItemDetalhesAct.EXTRA_ITEM, novidadesItem);
        startActivity(it);
    }

    private void atualizarLista() {
        NovidadesListAdapter adapter = new NovidadesListAdapter(getActivity(), mNovidadesItem);
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

    private class NovidadesDownloadTask extends AsyncTask<Void, Void, NovidadesItem[]>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            exibirProgresso();
        }

        @Override
        protected NovidadesItem[] doInBackground(Void... voids) {

            return NovidadesHttp.obterNovidadesDoServidor();
        }

        @Override
        protected void onPostExecute(NovidadesItem[] novidadesItems) {
            super.onPostExecute(novidadesItems);
            mSwipe.setRefreshing(false);
            if (novidadesItems!= null){
                mNovidadesItem = novidadesItems;
                atualizarLista();
            }
        }
    }
}
