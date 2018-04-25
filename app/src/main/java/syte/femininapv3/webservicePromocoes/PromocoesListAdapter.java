/*
 * Copyright (c) $today.year.SpheraSystems I.T - 2017
 */

package syte.femininapv3.webservicePromocoes;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import syte.femininapv3.R;
import syte.femininapv3.model.PromocoesItem;

public class PromocoesListAdapter extends RecyclerView.Adapter<PromocoesListAdapter.PromocaoViewHolder> {

    private Context mContext;

    private PromocoesItem[] mNovidadesItem;

    private AoClicarNaPromocaoListener mListener;

    private static final String PRE_LINK = "xxxxxxxxxxxxxxxxxxxxxxxxxxx";

    public PromocoesListAdapter(Context ctx,PromocoesItem[] promocoesItems){
        mContext = ctx;
        mNovidadesItem = promocoesItems;
    }

    public void setAoCLicarNaPromocaoListener( AoClicarNaPromocaoListener clickPromocao){
        mListener = clickPromocao;
        notifyDataSetChanged();
    }

    @Override
    public PromocaoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemViewNovidades = LayoutInflater.from(mContext).inflate(R.layout.novidades_list_row, parent,false);

        PromocaoViewHolder pvh = new PromocaoViewHolder(itemViewNovidades);

        itemViewNovidades.setTag(pvh);

        itemViewNovidades.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    PromocaoViewHolder pvh = (PromocaoViewHolder)view.getTag();
                    int position = pvh.getAdapterPosition();
                    mListener.aoClicarNoItemPromocoes(view, position, mNovidadesItem[position]);
                }
            }
        });

        return pvh;
    }

    @Override
    public void onBindViewHolder(PromocaoViewHolder holder, int position) {
        PromocoesItem promocoesItem = mNovidadesItem[position];


        Picasso.with(mContext).load(PRE_LINK+promocoesItem.foto).into(holder.imagePropaganda);
        //holder.txtCategoria.setText(promocoesItem.categoria);
        holder.txtSlogan.setText(promocoesItem.nome);
        holder.txtTextPropag.setText(promocoesItem.descricao);

    }
    public interface AoClicarNaPromocaoListener {
        void aoClicarNoItemPromocoes(View view, int position, PromocoesItem promocoesItem);

    }


    @Override
    public int getItemCount() {
        return mNovidadesItem != null? mNovidadesItem.length : 0;
    }

    public static class PromocaoViewHolder extends RecyclerView.ViewHolder{

        //@BindView(R.id.txtCategoria)
        //public TextView txtCategoria;

        @BindView(R.id.imagePropaganda)
        public ImageView imagePropaganda;

        @BindView(R.id.txtTextDescricao)
        public TextView txtTextPropag;
        @BindView(R.id.txtNomeProduto)
        public TextView txtSlogan;



        public PromocaoViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }
    }

}