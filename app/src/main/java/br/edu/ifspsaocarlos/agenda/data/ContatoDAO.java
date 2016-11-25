package br.edu.ifspsaocarlos.agenda.data;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifspsaocarlos.agenda.model.Contato;
import io.realm.Realm;
import io.realm.RealmResults;


public class ContatoDAO {
    private Realm realm;

    public ContatoDAO() {
        this.realm = Realm.getDefaultInstance();
    }

    /**
     * Busca cotatos com o nome especificado.
     * Se o nome for null, todos serão retornados
     * @param nome
     * @return contatos encontrados
     */
    public List<Contato> buscaContato(String nome) {
        RealmResults<Contato> results;
        List<Contato> contatos = new ArrayList();
        if(nome != null){
            //Busca contatos com o nome
            results = realm.where(Contato.class).equalTo("nome", nome).findAll();
        }else{
            //Busca todos os contatos
            results = realm.where(Contato.class).findAll();
        }
        if(!results.isEmpty()){
            for(int i = 0; i < results.size() ; i++){
                contatos.add(results.get(i));
                Log.i("CONTATO ", results.get(i).getNome());
            }
        }
        return contatos;
    }

    /**
     * Busca contato pelo id
     * @param id
     * @return contato encontrado ou null c.c
     */
    public Contato buscaContato(long id) {
        return realm.where(Contato.class).equalTo("id", id).findFirst();
    }

    /**
     * Atualiza um contato existente
     * @param id
     * @param nome
     * @param email
     * @param fone
     * @param fone2
     * @param dataAniversario
     */
    public void atualizaContato(long id, final String nome, final String email, final String fone, final String fone2, final String dataAniversario) {
        final Contato contatoExistente = realm.where(Contato.class).equalTo("id",id).findFirst();
        if(contatoExistente != null){
            realm.executeTransaction(new Realm.Transaction(){
                @Override
                public void execute(Realm realm) {
                    contatoExistente.setNome(nome);
                    contatoExistente.setFone(fone);
                    contatoExistente.setFone2(fone2);
                    contatoExistente.setEmail(email);
                    contatoExistente.setDataAniversario(dataAniversario);
                }
            });
        }
    }

    /**
     * Adiciona novo contato ao realm
     * @param c
     */
    public void insereContato(Contato c) {
        final Contato novoContato = new Contato();
        novoContato.setId(getNextIdContato());
        novoContato.setNome(c.getNome());
        novoContato.setEmail(c.getEmail());
        novoContato.setFone(c.getFone());
        novoContato.setFone2(c.getFone2());
        novoContato.setDataAniversario(c.getDataAniversario());

        realm.executeTransaction(new Realm.Transaction(){
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(novoContato);
            }
        });
    }

    /**
     * Apaga contato do realm
     * @param c
     */
    public void apagaContato(Contato c) {
        final RealmResults<Contato> results = realm.where(Contato.class).equalTo("id",c.getId()).findAll();
        if(!results.isEmpty()){
            realm.executeTransaction(new Realm.Transaction(){
                @Override
                public void execute(Realm realm) {
                    //irá encontrar apenas um, apaga esse elemento
                    results.deleteFirstFromRealm();
                }
            });
        }
    }

    /**
     * Retorna qual é o próximo
     * id válido para um contato
     * @return próximo id válido
     */
    private long getNextIdContato(){
        final RealmResults<Contato> results = realm.where(Contato.class).findAll();
        if(!results.isEmpty()){
            return (results.max("id").longValue()) + 1;
        }
        return 1;
    }
}
