package br.com.valdir.desafiolojastarwars.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class TransacoesDBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "transacoeslojastarwars.db";

    public TransacoesDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlTableUsuarios = "CREATE TABLE " + UsuariosContract.UsuarioEntry.TABLE_NAME + " (" +
                UsuariosContract.UsuarioEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                UsuariosContract.UsuarioEntry.COLUMN_NOME + " TEXT NOT NULL " +
                ");";

        db.execSQL(sqlTableUsuarios);

        String sqlTableTransacoes = "CREATE TABLE " + TransacoesContract.TransacaoEntry.TABLE_NAME + " (" +
                TransacoesContract.TransacaoEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TransacoesContract.TransacaoEntry.COLUMN_USUARIO_ID + " TEXT NOT NULL, " +
                TransacoesContract.TransacaoEntry.COLUMN_VALOR + " REAL, " +
                TransacoesContract.TransacaoEntry.COLUMN_DATA + " TEXT NOT NULL, " +
                TransacoesContract.TransacaoEntry.COLUMN_HORA + " TEXT NOT NULL, " +
                TransacoesContract.TransacaoEntry.COLUMN_ULT_4_DIGITOS_CARTAO + " TEXT NOT NULL, " +
                TransacoesContract.TransacaoEntry.COLUMN_PORTADOR_CARTAO_NOME_COMPLETO + " TEXT NOT NULL " +
                ");";

        db.execSQL(sqlTableTransacoes);

        String sqlTableUsuariosSaldo = "CREATE TABLE " + UsuariosSaldoContract.UsuarioSaldoEntry.TABLE_NAME + " (" +
                UsuariosSaldoContract.UsuarioSaldoEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                UsuariosSaldoContract.UsuarioSaldoEntry.COLUMN_USUARIO_ID + " TEXT NOT NULL, " +
                UsuariosSaldoContract.UsuarioSaldoEntry.COLUMN_SALDO_ATUAL + " REAL " +
                ");";

        db.execSQL(sqlTableUsuariosSaldo);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + TransacoesContract.TransacaoEntry.TABLE_NAME);
        db.execSQL("DROP TABLE " + UsuariosContract.UsuarioEntry.TABLE_NAME);
        db.execSQL("DROP TABLE " + UsuariosSaldoContract.UsuarioSaldoEntry.TABLE_NAME);

        onCreate(db);
    }

    public Cursor carregaDados(SQLiteDatabase db, Context context){
        Cursor cursor;
        String[] campos =  { TransacoesContract.TransacaoEntry._ID,
                TransacoesContract.TransacaoEntry.COLUMN_USUARIO_ID,
                TransacoesContract.TransacaoEntry.COLUMN_VALOR,
                TransacoesContract.TransacaoEntry.COLUMN_DATA,
                TransacoesContract.TransacaoEntry.COLUMN_HORA,
                TransacoesContract.TransacaoEntry.COLUMN_ULT_4_DIGITOS_CARTAO,
                TransacoesContract.TransacaoEntry.COLUMN_PORTADOR_CARTAO_NOME_COMPLETO };

        TransacoesDBHelper banco = new TransacoesDBHelper(context);

        db = banco.getReadableDatabase();
        cursor = db.query(TransacoesContract.TransacaoEntry.TABLE_NAME,
                campos, null, null, null, null, null, null);

        if(cursor!=null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }
}
