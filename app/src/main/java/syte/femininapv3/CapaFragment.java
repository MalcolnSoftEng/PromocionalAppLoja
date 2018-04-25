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
import syte.femininapv3.model.CapaModel;
import syte.femininapv3.webserviceCapa.CapaHttp;
import syte.femininapv3.webserviceCapa.CapaListAdapter;


public class CapaFragment extends Fragment implements CapaListAdapter.AoClicarNaCapaListener {

    @BindView(R.id.capaswipeRefresh)
    SwipeRefreshLayout mSwipe;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    Unbinder unbinder;

    private CapaModel[] mNovidadesItem;
    NovidadesDownloadTask mTask;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.capa_coordinator_layout, container, false);

        ButterKnife.bind(this, view);
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
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //} else {
        //mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
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
        if (mNovidadesItem == null) {
            if (mTask == null) {
                mTask = new NovidadesDownloadTask();
                mTask.execute();
            } else if (mTask.getStatus() == AsyncTask.Status.RUNNING) {
                exibirProgresso();
            }
        } else {
            atualizarLista();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mTask != null) mTask.cancel(true);
    }

    @Override
    public void aoClicarNaCapa(View view, int position, CapaModel novidadesItem) {
        Intent it = new Intent(getActivity(), NovidadesItemDetalhesAct.class);
        it.putExtra(NovidadesItemDetalhesAct.EXTRA_ITEM, novidadesItem);
        startActivity(it);
    }

    private void atualizarLista() {
        CapaListAdapter adapter = new CapaListAdapter(getActivity(), mNovidadesItem);
        adapter.setAoCLicarNaCapaListener(this);
        mRecyclerView.setAdapter(adapter);
    }

    public void exibirProgresso() {
        mSwipe.post(new Runnable() {
            @Override
            public void run() {
                mSwipe.setRefreshing(true);
            }
        });
    }

    private class NovidadesDownloadTask extends AsyncTask<Void, Void, CapaModel[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            exibirProgresso();
        }

        @Override
        protected CapaModel[] doInBackground(Void... voids) {

            return CapaHttp.obterNovidadesDoServidor();
        }

        @Override
        protected void onPostExecute(CapaModel[] novidadesItems) {
            super.onPostExecute(novidadesItems);
            mSwipe.setRefreshing(false);
            if (novidadesItems != null) {
                mNovidadesItem = novidadesItems;
                atualizarLista();
            }
        }
    }
}


/**
 * This interface must be implemented by activities that contain this
 * fragment to allow an interaction in this fragment to be communicated
 * to the activity and potentially other fragments contained in that
 * activity.
 * <p>
 * See the Android Training lesson <a href=
 * "http://developer.android.com/training/basics/fragments/communicating.html"
 * >Communicating with Other Fragments</a> for more information.
 */


