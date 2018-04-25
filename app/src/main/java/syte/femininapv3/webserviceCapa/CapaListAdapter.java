package syte.femininapv3.webserviceCapa;

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
import syte.femininapv3.model.CapaModel;


public class CapaListAdapter extends RecyclerView.Adapter<CapaListAdapter.CapaViewHolder> {

    private Context mContext;

    private CapaModel[] mNovidadesItem;

    private AoClicarNaCapaListener mListener;

    private static final String PRE_LINK = "http://xxxxxxxxxxxxxxxxxxxxx/";

    public CapaListAdapter(Context ctx, CapaModel[] novidadesItems){
        mContext = ctx;
        mNovidadesItem = novidadesItems;
    }

    public void setAoCLicarNaCapaListener( AoClicarNaCapaListener clickPromocao){
        mListener = clickPromocao;
        notifyDataSetChanged();
    }

    @Override
    public CapaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemViewNovidades = LayoutInflater.from(mContext).inflate(R.layout.capa_list_row, parent,false);

        CapaViewHolder pvh = new CapaViewHolder(itemViewNovidades);

        itemViewNovidades.setTag(pvh);

        itemViewNovidades.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    CapaViewHolder pvh = (CapaViewHolder)view.getTag();
                    int position = pvh.getAdapterPosition();
                    mListener.aoClicarNaCapa(view, position, mNovidadesItem[position]);
                }
            }
        });

        return pvh;
    }

    @Override
    public void onBindViewHolder(CapaViewHolder holder, int position) {
        CapaModel novidadesItem = mNovidadesItem[position];


        Picasso.with(mContext).load(PRE_LINK+novidadesItem.foto).into(holder.imagePropaganda);

        holder.txtSlogan.setText(novidadesItem.nome);
        holder.txtTextPropag.setText(novidadesItem.descricao);

    }
    public interface AoClicarNaCapaListener {
        void aoClicarNaCapa(View view, int position, CapaModel novidadesItem);

    }


    @Override
    public int getItemCount() {
        return mNovidadesItem != null? mNovidadesItem.length : 0;
    }

    public static class CapaViewHolder extends RecyclerView.ViewHolder{


        @BindView(R.id.imagePropaganda)
        public ImageView imagePropaganda;
        @BindView(R.id.txtTextDescricao)
        public TextView txtTextPropag;
        @BindView(R.id.txtNomeProduto)
        public TextView txtSlogan;



        public CapaViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }
    }

}