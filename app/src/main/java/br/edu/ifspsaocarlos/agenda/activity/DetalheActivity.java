package br.edu.ifspsaocarlos.agenda.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import br.edu.ifspsaocarlos.agenda.data.ContatoDAO;
import br.edu.ifspsaocarlos.agenda.model.Contato;
import br.edu.ifspsaocarlos.agenda.R;


public class DetalheActivity extends AppCompatActivity {
    private Contato c;
    private ContatoDAO cDAO;
    private long contatoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        cDAO = new ContatoDAO();

        if (getIntent().hasExtra("contato_id")) {
            this.contatoId = getIntent().getLongExtra("contato_id",0);
            this.c = cDAO.buscaContato(contatoId);
            if(c != null){
                EditText nameText = (EditText) findViewById(R.id.editText1);
                nameText.setText(c.getNome());
                EditText foneText = (EditText) findViewById(R.id.editText2);
                foneText.setText(c.getFone());

                EditText fone2Text = (EditText) findViewById(R.id.editText4);
                fone2Text.setText(c.getFone2());

                EditText emailText = (EditText) findViewById(R.id.editText3);
                emailText.setText(c.getEmail());

                TextView textViewBirth = (TextView) findViewById(R.id.editTextDateBirth);
                textViewBirth.setText(c.getDataAniversario());

                int pos = c.getNome().indexOf(" ");
                if (pos == -1)
                    pos = c.getNome().length();
                setTitle(c.getNome().substring(0, pos));
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detalhe, menu);
        if (!getIntent().hasExtra("contato")) {
            MenuItem item = menu.findItem(R.id.delContato);
            item.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.salvarContato:
                salvar();
                return true;
            case R.id.delContato:
                apagar();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void apagar() {
        cDAO.apagaContato(c);
        Intent resultIntent = new Intent();
        setResult(3, resultIntent);
        finish();
    }

    public void salvar() {
        String name = ((EditText) findViewById(R.id.editText1)).getText().toString();
        String fone = ((EditText) findViewById(R.id.editText2)).getText().toString();
        String fone2 = ((EditText) findViewById(R.id.editText4)).getText().toString();
        String email = ((EditText) findViewById(R.id.editText3)).getText().toString();
        String dataAniversario = ((EditText) findViewById(R.id.editTextDateBirth)).getText().toString();
        if (c == null) {
            c = new Contato();
            c.setNome(name);
            c.setFone(fone);
            c.setFone2(fone2);
            c.setEmail(email);
            c.setDataAniversario(dataAniversario);
            cDAO.insereContato(c);
        } else {
            cDAO.atualizaContato(c.getId(), name, email, fone, fone2, dataAniversario);
        }
        Intent resultIntent = new Intent();
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}

