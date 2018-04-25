/*
 * Copyright (c) $today.year.SpheraSystems I.T - 2017
 */

package syte.femininapv3;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import syte.femininapv3.model.PromocoesItem;

/**
 * Created by Malcoln on 02/11/2017.
 */

public class PromocoesItemDetalhesAct extends AppCompatActivity{
    public static final String EXTRA_ITEM = "promocoesItem";

    @BindView(R.id.fabFavorito)
    FloatingActionButton mFabFavorito;

    @BindView(R.id.imgPropaganda)
    ImageView mImagePropaganda;

    TextView mCategoria;

    TextView mNomeProduto;

    TextView mDescricao;

    TextView mPreco;

    @BindView(R.id.coordinator)
    CoordinatorLayout mCoordinator;


    AppBarLayout mAppBar;

    @BindView(R.id.collapseToolbar)
    CollapsingToolbarLayout mCollapsingToolbarLayout;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novidades_detalhe);
        ButterKnife.bind(this);

        mNomeProduto = (TextView)findViewById((R.id.txtNomeProduto));
        mDescricao = (TextView)findViewById(R.id.txtTextDescricao);
        //mCategoria = (TextView)findViewById(R.id.txtCategoria);
        mPreco = (TextView)findViewById(R.id.txtPreco);
        mAppBar = (AppBarLayout)findViewById(R.id.appBar);

        PromocoesItem promocoesItem = (PromocoesItem)getIntent().getSerializableExtra(EXTRA_ITEM);
        preencherCampos(promocoesItem);
        
        configurarBarraDeTitulo(promocoesItem.nome);
        
        carregarCapa(promocoesItem);

    }

    private void carregarCapa(PromocoesItem promocoesItem) {

        Picasso.with(this)
                .load("http://femenina.syte.com.br/css/produtos/" + promocoesItem.foto)
                .into(mImagePropaganda);
    }

    private void preencherCampos(PromocoesItem promocoesItem) {
        mNomeProduto.setText(promocoesItem.nome);
        mDescricao.setText(promocoesItem.descricao);
        mCategoria.setText(promocoesItem.categoria);
        mPreco.setText(promocoesItem.preco);
    }

    private void configurarBarraDeTitulo(String nome) {
        setSupportActionBar(mToolbar);
        if (mAppBar != null){
            if (mAppBar.getLayoutParams() instanceof CoordinatorLayout.LayoutParams){
                CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) mAppBar.getLayoutParams();
                lp.height = getResources().getDisplayMetrics().widthPixels;
            }
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (mCollapsingToolbarLayout!=null){
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            mCollapsingToolbarLayout.setTitle(nome);
        } else {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

}
