package syte.femininapv3.webserviceNovidades;

import android.content.Context;
import android.support.annotation.Nullable;
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
import syte.femininapv3.model.NovidadesItem;

/**
 * **************Uso de licensas**
 * Copyright 2013 Jake Wharton

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 *
 * Created by Malcoln on 26/07/2017.
 */

public class NovidadesListAdapter extends RecyclerView.Adapter<NovidadesListAdapter.PromocaoViewHolder> {

    private Context mContext;

    private NovidadesItem[] mNovidadesItem;

    private AoClicarNaPromocaoListener mListener;

    private static final String PRE_LINK = "xxxxxxxxxxxxxxxxxxxxxxxxx/";

    public NovidadesListAdapter(Context ctx, NovidadesItem[] novidadesItems){
        mContext = ctx;
        mNovidadesItem = novidadesItems;
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
                    mListener.aoClicarNoItemNovidades(view, position, mNovidadesItem[position]);
                }
            }
        });

        return pvh;
    }

    @Override
    public void onBindViewHolder(PromocaoViewHolder holder, int position) {
        NovidadesItem novidadesItem = mNovidadesItem[position];


        Picasso.with(mContext).load(PRE_LINK+novidadesItem.foto).into(holder.imagePropaganda);
        //holder.txtCategoria.setText(novidadesItem.categoria);
        holder.txtSlogan.setText(novidadesItem.nome);
        holder.txtTextPropag.setText(novidadesItem.descricao);

    }
    public interface AoClicarNaPromocaoListener {
        void aoClicarNoItemNovidades(View view, int position, NovidadesItem novidadesItem);

    }


    @Override
    public int getItemCount() {
        return mNovidadesItem != null? mNovidadesItem.length : 0;
    }

    public static class PromocaoViewHolder extends RecyclerView.ViewHolder{
        @Nullable
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