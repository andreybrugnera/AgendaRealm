package br.edu.ifspsaocarlos.agenda.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.edu.ifspsaocarlos.agenda.model.Contato;
import br.edu.ifspsaocarlos.agenda.R;

import java.util.List;


public class ContatoAdapter extends RecyclerView.Adapter<ContatoAdapter.ContatoViewHolder> {

    private List<Contato> contatos;
    private Context context;

    private static ItemClickListener clickListener;

    public ContatoAdapter(List<Contato> contatos, Context context) {
        this.contatos = contatos;
        this.context = context;
    }

    @Override
    public ContatoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.contato_celula, parent, false);
        return new ContatoViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ContatoViewHolder holder, int position) {
        Contato contato = contatos.get(position);
        holder.nome.setText(contato.getNome());
        holder.telefone.setText(contato.getFone());
        holder.telefone2.setText(contato.getFone2());
    }

    @Override
    public int getItemCount() {
        return contatos.size();
    }


    public void setClickListener(ItemClickListener itemClickListener) {
        clickListener = itemClickListener;
    }


    public class ContatoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final TextView nome;
        final TextView telefone;
        final TextView telefone2;

        public ContatoViewHolder(View view) {
            super(view);
            nome = (TextView) view.findViewById(R.id.nome);
            telefone = (TextView) view.findViewById(R.id.telefone);
            telefone2 = (TextView) view.findViewById(R.id.telefone_2);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null)
                clickListener.onItemClick(view, getAdapterPosition());
        }
    }


    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

}