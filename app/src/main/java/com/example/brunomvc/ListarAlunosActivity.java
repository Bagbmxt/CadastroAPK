package com.example.brunomvc;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.audiofx.Visualizer;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ListarAlunosActivity extends AppCompatActivity  {


    private Conexao conexao;
    private SQLiteDatabase banco;
    private ListView listView;
    private AlunoDAO dao;
    private List<Aluno> alunos;
    private List<Aluno> alunosFiltrados = new ArrayList<>();
    private ContextMenu menu;
    private View v;
    private ContextMenu.ContextMenuInfo menuInfo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_alunos);



        listView = findViewById(R.id.lista_alunos);
        dao = new AlunoDAO(this);
        alunos = dao.obterTodos();
        alunosFiltrados.addAll(alunos);
        //ArrayAdapter<Aluno> adaptador = new ArrayAdapter<Aluno>(this, android.R.layout.simple_list_item_1, alunosFiltrados);
        AlunoAdapter adaptador = new AlunoAdapter(this, alunosFiltrados);
        listView.setAdapter(adaptador);
        registerForContextMenu(listView);




    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater i = getMenuInflater();
        i.inflate(R.menu.menu_principal, menu);

        SearchView sv = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                procuraAluno(s);
                return false;
            }
        });

        return true;
    }
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) { /* compiled code */
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater i = getMenuInflater();
        i.inflate(R.menu.menu_contexto, menu);
    }


    public void procuraAluno(String nome){
        alunosFiltrados.clear();
        for(Aluno a : alunos){
            if(a.getNome().toLowerCase().contains(nome.toLowerCase())){
                alunosFiltrados.add(a);
            }
        }
        listView.invalidateViews();
    }

    public void excluir(MenuItem item){
        AdapterView.AdapterContextMenuInfo menuinfo =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
       final Aluno alunoExcluir = alunosFiltrados.get(menuinfo.position);

        AlertDialog dialog = new AlertDialog.Builder( this)
            .setTitle("Atenção")
                .setMessage("Realmente deseja excluir o aluno?")
                .setNegativeButton("NÃO",null)
                .setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        alunosFiltrados.remove(alunoExcluir);
                        alunos.remove(alunoExcluir);
                        dao.excluir(alunoExcluir);
                        listView.invalidateViews();

                    }
                }).create();
            dialog.show();
    }

    public void atualizar(MenuItem item){
        AdapterView.AdapterContextMenuInfo menuinfo =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
       final Aluno alunoAtualizar = alunosFiltrados.get(menuinfo.position);
       Intent it = new Intent(this, Cadastro.class);
       it.putExtra("aluno", alunoAtualizar);
       startActivity(it);

    }

    //Chamando Metodo botao cadastrar + "seguindo CadastroAlunoActivity e  android:onClick="cadastrar"
    public void cadastrar(MenuItem item){
        Intent it = new Intent(this, Cadastro.class);
        startActivity(it);
    }


   public void visualizar(MenuItem item) {
       AdapterView.AdapterContextMenuInfo menuinfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
       final Aluno alunoVisualizar = alunosFiltrados.get(menuinfo.position);

       Intent it = new Intent(this, detalhes.class);
       it.putExtra("aluno", alunoVisualizar);
       startActivity(it);

       }
           /*       }
        AdapterView.AdapterContextMenuInfo menuinfo =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final Aluno alunoAtualizar = alunosFiltrados.get(menuinfo.position);
        Intent it = new Intent(this, detalhes.class);
        it.putExtra("aluno", alunoAtualizar);
        startActivity(it);*/




    @Override
    public void onResume(){
        super.onResume();
        alunos = dao.obterTodos();
        alunosFiltrados.clear();
        alunosFiltrados.addAll(alunos);
        listView.invalidateViews();
    }




}