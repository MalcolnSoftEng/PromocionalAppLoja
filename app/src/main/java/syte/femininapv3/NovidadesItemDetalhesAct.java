/*
 * Copyright (c) $today.year.SpheraSystems I.T - 2017
 */

package syte.femininapv3;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import syte.femininapv3.Controller.ProdutoAdapter;
import syte.femininapv3.model.NovidadesItem;

/**
 * Created by Malcoln on 02/11/2017.
 */

public class NovidadesItemDetalhesAct extends AppCompatActivity{

    public static final String EXTRA_ITEM = "novidadesItem";

    ////////////////////////////////////////////////////
    /////////////COMPONENTES E ATRIBUTOS REFERENTES A VIEWPAGER DAS IMAGENS WEBSERVICE ARRAY
    private ViewPager viewPager;
    private ProdutoAdapter mProdutoViewPagerAdapter;
    private LinearLayout dotsLayout;
    private ImageButton btnGo, btnReturn;
    private TextView[] dots;
    int[] layout = new int[]{R.layout.activity_inicial,
            R.layout.activity_inicial2, R.layout.activity_inicial3,R.layout.activity_inicial4,R.layout.activity_inicial5};

    ///////////////////////////////////////////////////

    @BindView(R.id.fabFavorito)
    FloatingActionButton mFabFavorito;

    @BindView(R.id.imgPropaganda)
    ImageView mImagePropaganda;

    //TextView mCategoria;

    TextView mNomeProduto;

    TextView mDescricao;

    TextView mPreco;

    @BindView(R.id.coordinator) // activity_novidades_detalhes.xml
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

        ImageView imageUpLoadWS = (ImageView) findViewById(R.id.imageUpLoadWS);

        NovidadesItem novidadesItem = (NovidadesItem)getIntent().getSerializableExtra(EXTRA_ITEM);
        preencherCampos(novidadesItem);
        
        configurarBarraDeTitulo(novidadesItem.nome);
        
        carregarCapa(novidadesItem);


        //////////////////////////////////////////////////////////
        ///////// INSTANCIAS/OBJETOS REFERENTES A VIEWPAGER DAS IMAGENS DE WEBSERVICE
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        dotsLayout = (LinearLayout) findViewById(R.id.layoutDots);
        btnReturn = (ImageButton)findViewById(R.id.imageButtonLeft);
        btnGo = (ImageButton) findViewById(R.id.imageButtonRight);

        mProdutoViewPagerAdapter = new ProdutoAdapter(this, layout, novidadesItem.fotos);


        viewPager.setAdapter(mProdutoViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int viewAnterior = getItem(-1);
                if (viewAnterior<mProdutoViewPagerAdapter.getCount()){
                    viewPager.setCurrentItem(viewAnterior);
                }
            }
        });

        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int viewAtual = getItem(+1);
                int viewAnterior = getItem(-1);
                if (viewAtual < mProdutoViewPagerAdapter.getCount()){
                    viewPager.setCurrentItem(viewAtual);
                } else {
                    viewPager.setCurrentItem(viewAnterior);
                }
            }
        });

        // adding bottom dots
        addBottomDots(0);

    }

    private void carregarListaFotos(String[] fotos) {

    }

    private void carregarCapa(NovidadesItem novidadesItem) { // carregar ARRAY de String de Json

        Picasso.with(this)
                .load("http://femenina.syte.com.br/css/produtos/" + novidadesItem.foto)
                .into(mImagePropaganda);

        // carregar ARRAY de String de Json
    }


    private void preencherCampos(NovidadesItem novidadesItem) {
        mNomeProduto.setText(novidadesItem.nome);
        mDescricao.setText(novidadesItem.descricao);
    //    mCategoria.setText(novidadesItem.categoria);
        mPreco.setText(novidadesItem.preco);
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
            getSupportActionBar().setIcon(R.drawable.ic_launcher);
            mCollapsingToolbarLayout.setTitle(nome);
        } else {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    //////////////////////////////////////////////////////////////////////
    //////////////// MÉTODOS E CLASSE REFERETES A VIEWPAGER DAS IMAGENS WEBSERVICE

    private void addBottomDots(int currentPage) {
        dots = new TextView[mProdutoViewPagerAdapter.getCount()];

        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorsInactive[currentPage]);
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(colorsActive[currentPage]);
    }

    private int getItem(int i){
        return  viewPager.getCurrentItem()+i;
    }

    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener(){

        @Override
        public void onPageSelected(int position) {

        }
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

}
