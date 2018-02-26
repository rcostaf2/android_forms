package com.aroos.metacom.activities.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by murilo on 02/04/2016.
 */
public  class DbHelper extends SQLiteOpenHelper {
     //private static final String NOME_BASE = "osaltarede_mob";
    //altarede
//    private static final String NOME_BASE = "aroos_mob";
//    private static final int VERSAO_BASE = 1;
    //public String ROOT_URL = "http://aroos.altarede.com.br/altarede";
//    public String ROOT_URL = "http://177.130.1.206/altarede";
    // metacom
    private static final String NOME_BASE = "aroos_metacom";
    private static final int VERSAO_BASE = 1;
    public String ROOT_URL = "http://metacom.aroos.com.br";
//    public String ROOT_URL = "http://fibracom.aroos.com.br";

    //public String ROOT_URL = "http://aroos.altarede.com.br/altarede";


   // public String ROOT_URL = "http://177.130.1.206/metacom";

    //private static final String DATABASE_ALTER_TEAM_2 = "ALTER TABLE "
      //      + " USERS " + " ADD COLUMN " + " PERFIL " + " string;";
   String sqlCreatePonto = " CREATE TABLE PONTOS( id INTEGER PRIMARY KEY AUTOINCREMENT, " +
           "  tecnico_id INTEGER, acao_hora TEXT, data TEXT, hora TEXT, " +
           "  latitude TEXT, longitude TEXT, enviado TEXT)";

    String sqlCreateEstacoes = " CREATE TABLE ESTACOES( id INTEGER PRIMARY KEY , " +
            "  tecnico_id INTEGER, localidade TEXT, estacao TEXT, descricao TEXT , classe TEXT, fornecedor TEXT, tipos_obra TEXT, armario TEXT" +
            "  , data_encerrado TEXT, obs TEXT, endereco TEXT, id_obra TEXT)";

    String sqlCreateServicos = " CREATE TABLE SERVICOS( id INTEGER PRIMARY KEY , " +
            "  tecnico_id INTEGER, fornecedor TEXT, codigo TEXT, descricao TEXT, unidade TEXT, " +
            "   ordem INTEGER, tipo TEXT, classe TEXT)";

    String sqlCreateDCapa = " CREATE TABLE DCAPAS( id INTEGER PRIMARY KEY , " +
            "  descricao TEXT, classe TEXT, " +
            "   ordem INTEGER)";
    String sqlCreateDCapaList = " CREATE TABLE DCAPAList( id INTEGER PRIMARY KEY , " +
            "  group_name TEXT, valor TEXT, " +
            "   ordem INTEGER)";
    String sqlCreateDiarioItems = " CREATE TABLE DIARIO_ITEMS( id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "  estacao_id INTEGER,  tecnico_id INTEGER, info TEXT, " +
            "  capa TEXT, servicos TEXT, imagem TEXT, keySave TEXT)";
//    String sqlCreateDiarioLayout = " CREATE TABLE DIARIO_LAYOUT( id INTEGER PRIMARY KEY AUTOINCREMENT, " +
//            "   numero TEXT, " +
//            "  acao_hora TEXT, data_inicio TEXT, data_termino TEXT, sincronizado TEXT)";

    String sqlCreateDiario = " CREATE TABLE DIARIOS( id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "  tecnico_id INTEGER, estacao_id INTEGER, tipo TEXT," +
            "  acao_hora TEXT, data_inicio TEXT, data_termino TEXT, sincronizado TEXT)";

    String sqlCreateDiarioCapa = " CREATE TABLE DIARIO_CAPA( id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "  diario_id INTEGER, item TEXT, valor TEXT," +
            "  tipo TEXT)";


    public DbHelper(Context context) {
        super(context, NOME_BASE, null, VERSAO_BASE);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlCreateUser = " CREATE TABLE USERS( id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "external_id INTEGER, nome TEXT, email TEXT, login TEXT, senha TEXT," +
                "path_assinatura TEXT, file_name_assinatura TEXT, in_use integer, perfil integer )";
        db.execSQL(sqlCreateUser);
        String sqlCreateAtividade = " CREATE TABLE ATIVIDADES( id INTEGER, " +
                " mercado TEXT, nr_os TEXT," +
                "  tipo TEXT, abertura TEXT, periodo TEXT, agendamento TEXT, solicitante TEXT," +
                " cliente TEXT, telefone TEXT, celular TEXT,  observacao_abertura TEXT, atendente TEXT, tipo_logr TEXT, logradouro TEXT, numero TEXT, quadra TEXT, complemento TEXT, bairro TEXT, cidade TEXT, uf TEXT, cep TEXT," +
                " referencia TEXT, latitude TEXT, longitude TEXT, ip TEXT , mac TEXT, login TEXT, senha TEXT, plano TEXT,km_inicio TEXT, km_fim TEXT, antena TEXT, prop_equipamento TEXT, conexao TEXT, nr_cabo TEXT, par_eletrico TEXT, servidor TEXT,"+
                " ponto_acesso TEXT, defeito_reclamado TEXT, desiguinacao TEXT,  pop TEXT, interface_ TEXT,swit TEXT, porta TEXT, status_fechamento TEXT, tecnico_id INTEGER, ordem INTEGER, lat_porta TEXT, long_porta TEXT, lat_switch TEXT, long_switch TEXT)";
        db.execSQL(sqlCreateAtividade);
        String sqlCreateDespacho = " CREATE TABLE DESPACHOS( id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " atividade_id INTEGER, acao TEXT," +
                "  tecnico_id INTEGER, acao_hora TEXT, assinatura TEXT, data_encerramento TEXT, reparo_efetuado TEXT," +
                " observacao TEXT, contato_local TEXT, documento TEXT,  assinatura_1 TEXT, assinatura_2 TEXT, latitude TEXT, longitude TEXT)";
        db.execSQL(sqlCreateDespacho);
        db.execSQL(sqlCreatePonto);
// aroos novo
        db.execSQL(sqlCreateEstacoes);
        db.execSQL(sqlCreateServicos);
        db.execSQL(sqlCreateDCapa);
        db.execSQL(sqlCreateDCapaList);
        db.execSQL(sqlCreateDiarioItems);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       // if (oldVersion < 8) {
//            String sqlDropUser = " DROP TABLE USERS";
//            db.execSQL(sqlDropUser);
//            String sqlDropAtividade = " DROP TABLE ATIVIDADES";
//            db.execSQL(sqlDropAtividade);
//            String sqlDropDespach = " DROP TABLE DESPACHOS";
//            db.execSQL(sqlDropDespach);
//            String sqlDropPonto = " DROP TABLE PONTOS";
//            db.execSQL(sqlDropPonto);
//            onCreate(db);
      //  }
        if (newVersion == 2) {
//            db.execSQL(sqlCreateEstacoes);
//            db.execSQL(sqlCreateServicos);
//            db.execSQL(sqlCreateDCapa);
        }
    }


    public void cleanDiarioItems(int id) {
        SQLiteDatabase db =  getWritableDatabase();
        String sqlDeleteEstacao = " DELETE FROM diario_items where id = " + Integer.toString(id);
        db.execSQL(sqlDeleteEstacao);

    }
    public DiarioItem selectNextDiarioItem(int user_id){

        SQLiteDatabase db =  getReadableDatabase();
        DiarioItem u = new DiarioItem();
        String sql = " SELECT a.* FROM diario_items a where   a.tecnico_id="+user_id+"  " ;
        //String sql = " SELECT a.* FROM ATIVIDADES a ";
        Cursor c = db.rawQuery(sql, null);
        if (c.moveToFirst()){
            u.setId(c.getInt(0));
            u.setEstacao_id(c.getInt(1));
            u.setTecnico_id(c.getInt(2));
            u.setInfo(c.getString(3));
            u.setCapa(c.getString(4));
            u.setServicos(c.getString(5));
            u.setImagem(c.getString(6));
            u.setKeysave(c.getString(7));
        }
        db.close();
        return u;
    }

    public ArrayList<DiarioItem> selectDiarioItemsAll(int user_id){
        ArrayList<DiarioItem> listDiarioItem = new ArrayList<DiarioItem>();
        SQLiteDatabase db =  getReadableDatabase();
        String sql = " SELECT a.* FROM diario_items a where   a.tecnico_id="+user_id+"  " ;
        //String sql = " SELECT a.* FROM ATIVIDADES a ";
        Cursor c = db.rawQuery(sql, null);
        if (c == null) return listDiarioItem;

        try {
            if (c.moveToFirst()){
                do{
                    DiarioItem u = new DiarioItem();
                    u.setId(c.getInt(0));
                    u.setEstacao_id(c.getInt(1));
                    u.setTecnico_id(c.getInt(2));
                    u.setInfo(c.getString(3));
                    u.setCapa(c.getString(4));
                    u.setServicos(c.getString(5));
                    u.setImagem(c.getString(6));
                    u.setKeysave(c.getString(7));
                    listDiarioItem.add(u);
                }while (c.moveToNext());
            }
        } finally {
            c.close();
            db.close();
            return listDiarioItem;
        }


    }

    public ArrayList<DiarioItem> selectDiarioItems(int user_id, int estacao_id){
        ArrayList<DiarioItem> listDiarioItem = new ArrayList<DiarioItem>();
        SQLiteDatabase db =  getReadableDatabase();
        String sql = " SELECT a.* FROM diario_items a where  a.estacao_id="+estacao_id+" and a.tecnico_id="+user_id+"  " ;
        //String sql = " SELECT a.* FROM ATIVIDADES a ";
        Cursor c = db.rawQuery(sql, null);
        if (c == null) return listDiarioItem;

        try {
            if (c.moveToFirst()){
                do{
                    DiarioItem u = new DiarioItem();
                    u.setId(c.getInt(0));
                    u.setEstacao_id(c.getInt(1));
                    u.setTecnico_id(c.getInt(2));
                    listDiarioItem.add(u);
                }while (c.moveToNext());
            }
        } finally {
            c.close();
            db.close();
            return listDiarioItem;
        }


    }

    public void insertDiarioItem( DiarioItem ditem){
        ContentValues cv = new ContentValues();
        cv.put("estacao_id",ditem.getEstacao_id());
        cv.put("tecnico_id", ditem.getTecnico_id());
        cv.put("info", ditem.getInfo());
        cv.put("capa", ditem.getCapa());
        cv.put("servicos", ditem.getServicos());
        cv.put("imagem", ditem.getImagem());
        cv.put("keysave", ditem.getKeysave());
        SQLiteDatabase db =  getWritableDatabase();
        db.insert("diario_items", null, cv);
        db.close();
    }

    public Dcapa getDcapaById(int id){
        SQLiteDatabase db =  getReadableDatabase();
        String sql = " SELECT * FROM DCAPAS WHERE id=" +id+"" ;
        Cursor c = db.rawQuery(sql, null);
        Dcapa u = null;
        if (c.moveToFirst()){
            u = new Dcapa();
            u.setId(c.getInt(0));
            u.setDescricao(c.getString(1));
            u.setClasse(c.getString(2));
            u.setOrdem(c.getInt(3));
        }
        db.close();
        return u;
    }

    public void insertDcapa( Dcapa dcapa){
        ContentValues cv = new ContentValues();
        cv.put("id",dcapa.getId());
        cv.put("descricao", dcapa.getDescricao());
        cv.put("classe", dcapa.getClasse());
        cv.put("ordem", dcapa.getOrdem());
        Dcapa upE = getDcapaById(dcapa.getId());
        SQLiteDatabase db =  getWritableDatabase();
        if(upE==null){
            db.insert("dcapas", null, cv);
        }
        else {
            db.update("dcapas", cv, " ID = ?", new String[]{(Integer.toString(upE.getId()))});
        }
        db.close();
    }

    public List<String> selectDcapaList(String group_name){
        List<String> list = new ArrayList<String>();

        SQLiteDatabase db =  getReadableDatabase();

        String sql = " SELECT distinct  a.valor FROM dcapalist a where  a.group_name='"+group_name+"' order by ordem   " ;
        //String sql = " SELECT a.* FROM ATIVIDADES a ";
        Cursor c = db.rawQuery(sql, null);
        if (c.moveToFirst()){
            do{

                list.add(c.getString(0));

            }while (c.moveToNext());

        }
        db.close();
        return list;
    }

    public void insertDcapaList( DcapaList dcapa){
        ContentValues cv = new ContentValues();
        cv.put("id",dcapa.getId());
        cv.put("group_name", dcapa.getGroup_name());
        cv.put("valor", dcapa.getValor());
        cv.put("ordem", dcapa.getOrdem());
        SQLiteDatabase db =  getWritableDatabase();
        db.insert("dcapalist", null, cv);
        db.close();
    }
    public List<String> selectFornecedores(int user_id){
        List<String> list = new ArrayList<String>();

        SQLiteDatabase db =  getReadableDatabase();

        String sql = " SELECT distinct  a.fornecedor FROM servicos a where  a.tecnico_id='"+user_id+"' order by fornecedor   " ;
        //String sql = " SELECT a.* FROM ATIVIDADES a ";
        Cursor c = db.rawQuery(sql, null);
        if (c.moveToFirst()){
            do{

                list.add(c.getString(0));

            }while (c.moveToNext());

        }
        db.close();
        return list;
    }
    public ArrayList<Servico> selectServicos(String fornecedor,int user_id, String tipo, String classe){
        ArrayList<Servico> listServico = new ArrayList<Servico>();
        SQLiteDatabase db =  getReadableDatabase();

        String sql = " SELECT a.* FROM servicos a where  a.classe='"+classe+"' and a.tipo='"+tipo+"' and a.fornecedor = '"+ fornecedor + "' and a.tecnico_id='"+user_id+"' order by ordem " ;
        //String sql = " SELECT a.* FROM ATIVIDADES a ";
        Cursor c = db.rawQuery(sql, null);
        if (c.moveToFirst()){
            do{

                Servico u = new Servico();
                u.setId(c.getInt(0));
                u.setTecnico_id(c.getInt(1));
                u.setFornecedor(c.getString(2));
                u.setCodigo(c.getString(3));
                u.setDescricao(c.getString(4));
                u.setUnidade(c.getString(5));
                u.setOrdem(c.getInt(6));
                u.setTipo(c.getString(7));
                u.setClasse(c.getString(8));
                listServico.add(u);
            }while (c.moveToNext());

        }
        db.close();
        return listServico;
    }

    public ArrayList<Dcapa> selectDcapas(String classe,Estacao estacao){
        ArrayList<Dcapa> listDcapa = new ArrayList<Dcapa>();
        SQLiteDatabase db =  getReadableDatabase();

        String sql = " SELECT a.* FROM dcapas a where a.classe = '"+ classe + "' order by ordem " ;
        //String sql = " SELECT a.* FROM ATIVIDADES a ";
        Cursor c = db.rawQuery(sql, null);
        if (c.moveToFirst()){
            do{

                Dcapa u = new Dcapa();
                u.setId(c.getInt(0));
                u.setDescricao(c.getString(1));
                u.setClasse(c.getString(2));
                u.setOrdem(c.getInt(3));
                if (u.getDescricao().equals("ID")){
                    u.setValor(estacao.getId_obra());
                }
                if (u.getDescricao().equals("Tipo de Obra")){
                    u.setValor(estacao.getTipos_obra());
                }
                if (u.getDescricao().equals("Armário (ARD/ARDO)")){
                    u.setValor(estacao.getArmario());
                }
                if (u.getDescricao().equals("Endereço")){
                    u.setValor(estacao.getEndereco());
                }
                if (u.getDescricao().equals("Obs")){
                    u.setValor(estacao.getObs());
                }
                if (u.getDescricao().equals("OS/IONIX/INSTANCIA")){
                    u.setValor(estacao.getEstacao());
                }
                u.list = selectDcapaList(u.getDescricao());
                listDcapa.add(u);
            }while (c.moveToNext());

        }
        db.close();
        return listDcapa;
    }

    public ArrayList<Estacao> selectEstacoes(int user_id){
        ArrayList<Estacao> listEstacao = new ArrayList<Estacao>();
        SQLiteDatabase db =  getReadableDatabase();

        String sql = " SELECT a.* FROM estacoes a where a.tecnico_id = "+ user_id ;
        //String sql = " SELECT a.* FROM ATIVIDADES a ";
        Cursor c = db.rawQuery(sql, null);
        if (c.moveToFirst()){
            do{

                Estacao u = new Estacao();
                u.setId(c.getInt(0));
                u.setTecnico_id(c.getInt(1));
                u.setLocalidade(c.getString(2));
                u.setEstacao(c.getString(3));
                u.setDescricao(c.getString(4));
                u.setClasse(c.getString(5));
                u.setFornecedor(c.getString(6));
                u.setTipos_obra(c.getString(7));
                u.setArmario(c.getString(8));
                u.setData_encerrado(c.getString(9));
                u.setObs(c.getString(10));
                u.setEndereco(c.getString(11));
                u.setId_obra(c.getString(12));
                u.listDiarioItem = selectDiarioItems(u.getTecnico_id(), u.getId());
                listEstacao.add(u);
            }while (c.moveToNext());

        }
        db.close();
        return listEstacao;
    }
    public Estacao getEstacaoById(int id){
        SQLiteDatabase db =  getReadableDatabase();
        String sql = " SELECT * FROM ESTACOES WHERE id=" +id+"" ;
        Cursor c = db.rawQuery(sql, null);
        Estacao u = null;
        if (c.moveToFirst()){
            u = new Estacao();
            u.setId(c.getInt(0));
            u.setTecnico_id(c.getInt(1));
            u.setLocalidade(c.getString(2));
            u.setEstacao(c.getString(3));
            u.setDescricao(c.getString(4));
            u.setClasse(c.getString(5));
            u.setFornecedor(c.getString(6));
            u.setTipos_obra(c.getString(7));
            u.setArmario(c.getString(8));
            u.setObs(c.getString(10));
            u.setEndereco(c.getString(11));
            u.setId_obra(c.getString(12));
        }
        db.close();
        return u;
    }
    public void cleanEstacoes(int tecnico_id) {
        SQLiteDatabase db =  getWritableDatabase();
        String sqlDeleteEstacao = " DELETE FROM ESTACOES where tecnico_id = " + Integer.toString(tecnico_id);
        db.execSQL(sqlDeleteEstacao);

    }
    public void cleanDcapas() {
        SQLiteDatabase db =  getWritableDatabase();
        String sqlDeleteEstacao = " DELETE FROM DCAPAS  " ;
        db.execSQL(sqlDeleteEstacao);

    }
    public void cleanDcapasList() {
        SQLiteDatabase db =  getWritableDatabase();
        String sqlDeleteEstacao = " DELETE FROM DCAPAList  " ;
        db.execSQL(sqlDeleteEstacao);

    }
    public void cleanServicos() {
        SQLiteDatabase db =  getWritableDatabase();
        String sqlDeleteEstacao = " DELETE FROM SERVICOS  " ;
        db.execSQL(sqlDeleteEstacao);

    }
    public void insertEstacao( Estacao estacao){
        ContentValues cv = new ContentValues();
        cv.put("id",estacao.getId());
        cv.put("tecnico_id",estacao.getTecnico_id());
        cv.put("localidade",estacao.getLocalidade());
        cv.put("estacao",estacao.getEstacao());
        cv.put("descricao", estacao.getDescricao());
        cv.put("classe", estacao.getClasse());
        cv.put("fornecedor", estacao.getFornecedor());
        cv.put("tipos_obra", estacao.getTipos_obra());
        cv.put("armario", estacao.getArmario());
        cv.put("obs", estacao.getObs());
        cv.put("endereco", estacao.getEndereco());
        cv.put("id_obra", estacao.getId_obra());
        Estacao upE = getEstacaoById(estacao.getId());
        SQLiteDatabase db =  getWritableDatabase();

        if(upE==null){
            db.insert("estacoes", null, cv);
        }
        else {

            db.update("estacoes", cv, " ID = ?", new String[]{(Integer.toString(upE.getId()))});
        }
        db.close();
    }

    public Servico getServicoById(int id){
        SQLiteDatabase db =  getReadableDatabase();
        String sql = " SELECT * FROM SERVICOS WHERE id=" +id+"" ;
        Cursor c = db.rawQuery(sql, null);
        Servico u = null;
        if (c.moveToFirst()){
            u = new Servico();
            u.setId(c.getInt(0));
            u.setTecnico_id(c.getInt(1));
            u.setFornecedor(c.getString(2));
            u.setCodigo(c.getString(3));
            u.setDescricao(c.getString(4));
            u.setUnidade(c.getString(5));
            u.setOrdem(c.getInt(6));
            u.setTipo(c.getString(7));
            u.setClasse(c.getString(8));
        }
        db.close();
        return u;
    }

    public void insertServico( Servico servico){

        ContentValues cv = new ContentValues();
        cv.put("id",servico.getId());
        cv.put("tecnico_id",servico.getTecnico_id());
        cv.put("fornecedor",servico.getFornecedor());
        cv.put("codigo",servico.getCodigo());
        cv.put("descricao", servico.getDescricao());
        cv.put("unidade", servico.getUnidade());
        cv.put("ordem", servico.getOrdem());
        cv.put("tipo", servico.getTipo());
        cv.put("classe", servico.getClasse());
        Servico upE = getServicoById(servico.getId());
        SQLiteDatabase db =  getWritableDatabase();
        if(upE==null){
            db.insert("servicos", null, cv);
        }
        else {

            db.update("servicos", cv, " ID = ?", new String[]{(Integer.toString(upE.getId()))});
        }
        db.close();
    }
    public void updatePonto(int ponto_id) {
        SQLiteDatabase db =  getWritableDatabase();
        String sqlDeleteAtividade = " UPDATE PONTOS SET enviado='Sim' where id = " + Integer.toString(ponto_id);
        db.execSQL(sqlDeleteAtividade);


    }
    public void cleanAtividade(int atividade_id) {
        SQLiteDatabase db =  getWritableDatabase();
        String sqlDeleteAtividade = " DELETE FROM ATIVIDADES where id = " + Integer.toString(atividade_id);
        db.execSQL(sqlDeleteAtividade);
        String sqlDeleteAtividadeD = " DELETE FROM DESPACHOS where atividade_id = " + Integer.toString(atividade_id);
        db.execSQL(sqlDeleteAtividadeD);

    }

    public void cleanStatusAtiv(int tecnico_id) {
        SQLiteDatabase db =  getWritableDatabase();
        String sqlUpdateAtividade = " update ATIVIDADES  set status_fechamento ='EXCLUIR' where coalesce(status_fechamento,'') = '' and tecnico_id = " + Integer.toString(tecnico_id);
        db.execSQL(sqlUpdateAtividade);
    }
    public void cleanStatusDelAtiv(int tecnico_id) {
        SQLiteDatabase db =  getWritableDatabase();
        String sqlDeleteAtividade = " delete from ATIVIDADES   where status_fechamento = 'EXCLUIR' and tecnico_id = " + Integer.toString(tecnico_id);
       db.execSQL(sqlDeleteAtividade);
    }
    public void cleanPontos(int tecnico_id) {
        SQLiteDatabase db =  getWritableDatabase();
        String sqlDeleteAtividade = " delete from PONTOS    where "+
                " (julianday() - case when length(trim(data)) = 10 then  julianday(substr(acao_hora,1,10)) " +
                " when length(trim(data)) = 9 and not like(substr(acao_hora,8,2),'-') then julianday(substr(acao_hora,1,8)||'0'|| substr(acao_hora,9,1)) " +
                " when length(trim(data)) = 9 and like(substr(acao_hora,8,2),'-')  then julianday(substr(acao_hora,1,5)||'0'|| substr(acao_hora,6,3)) " +
                " when length(trim(data)) = 8 then julianday(substr(acao_hora,1,5)||'0'|| substr(acao_hora,6,2)||'0'|| substr(acao_hora,8,1))" +
                "  end) > 30 and   tecnico_id = "+ Integer.toString(tecnico_id);
        db.execSQL(sqlDeleteAtividade);
    }

    public Boolean test10minPontos(int tecnico_id, String data) {

        DataHelper dh =new DataHelper();
        String dtAtua = dh.getData_completa();
        SQLiteDatabase db =  getReadableDatabase();//where (julianday() - julianday('"+data+"') )*1000 > 8 and
        String sql = " select  (julianday('"+dtAtua+"') - julianday('"+data+"'))*1000 ,julianday() d,julianday('"+data+"')d2 ,t.*"+
                "  from PONTOS t   WHERE id in (select max(id) from pontos where tecnico_id = "+ Integer.toString(tecnico_id)+" )";
        Boolean result =false;
        Cursor c = db.rawQuery(sql, null);
        if (c.moveToFirst()){
            do{
                int aux1 = c.getInt(0);
                //String aux2 = c.getString(1);
               // String aux3 = c.getString(2);
                if (aux1 < 7)
                    result=true;

            }while (c.moveToNext());

        }
        db.close();
        return result;
    }
    public Ponto insertPonto( Ponto ponto){

        ContentValues cv = new ContentValues();


        cv.put("tecnico_id",ponto.getTecnico_id());
        cv.put("acao_hora", ponto.getAcao_hora());
        cv.put("data",ponto.getData());
        cv.put("hora",ponto.getHora());
        cv.put("latitude",ponto.getLatitude());
        cv.put("longitude",ponto.getLongitude());
        cv.put("enviado",ponto.getEnviado());
        SQLiteDatabase db =  getWritableDatabase();
        db.insert("pontos", null, cv);
        db.close();

        String sql = " SELECT max(id) FROM PONTOS a  ";
        Ponto aux = new Ponto();
        SQLiteDatabase db2 =  getReadableDatabase();
        Cursor c = db2.rawQuery(sql, null);
        if (c.moveToFirst()){
            do{
                aux.setId(c.getInt(0));

            }while (c.moveToNext());

        }
        db2.close();
        return aux;
    }

    public ArrayList<Ponto> selectPontos(int user_id, boolean enviado){
        ArrayList<Ponto> listPonto = new ArrayList<Ponto>();
        SQLiteDatabase db =  getReadableDatabase();
        String conditions= " and 1 =1 order by id desc ";
        final Calendar ca = Calendar.getInstance();
        String str = (new StringBuilder()
                // Month is 0 based, just add 1
                .append(ca.get(Calendar.YEAR)).append("-").append(ca.get(Calendar.MONTH) + 1).append("-")
                .append( ca.get(Calendar.DAY_OF_MONTH)).append(" ")).toString();
        if (enviado)
        {
            conditions = " and coalesce(enviado,'')='' order by id desc ";
        }

        cleanPontos(user_id);
        //data='"+ str+"'  and julianday() - julianday('2017-2-05')
        String sql = " SELECT a.*,  like(substr(acao_hora,1,10),'-') delt," +
                " julianday() - case when length(trim(data)) = 10 then  julianday(substr(acao_hora,1,10)) " +
              " when length(trim(data)) = 9 and not like(substr(acao_hora,8,2),'-') then julianday(substr(acao_hora,1,8)||'0'|| substr(acao_hora,9,1)) " +
                " when length(trim(data)) = 9 and like(substr(acao_hora,8,2),'-')  then julianday(substr(acao_hora,1,5)||'0'|| substr(acao_hora,6,3)) " +
                " when length(trim(data)) = 8 then julianday(substr(acao_hora,1,5)||'0'|| substr(acao_hora,6,2)||'0'|| substr(acao_hora,8,1))" +
                 "  end dt " + //end
                " FROM PONTOS a where    a.tecnico_id = "+ user_id + conditions;
       // " (julianday() - case when length(trim(data)) = 10 then  julianday(substr(acao_hora,1,10)) " +
       //         " when length(trim(data)) = 9 and not like(substr(acao_hora,8,2),'-') then julianday(substr(acao_hora,1,8)||'0'|| substr(acao_hora,9,1)) " +
       //         " when length(trim(data)) = 9 and like(substr(acao_hora,8,2),'-')  then julianday(substr(acao_hora,1,5)||'0'|| substr(acao_hora,6,3)) " +
       //         " when length(trim(data)) = 8 then julianday(substr(acao_hora,1,5)||'0'|| substr(acao_hora,6,2)||'0'|| substr(acao_hora,8,1))" +
       //         "  end) > 10 and

        Cursor c = db.rawQuery(sql, null);
        if (c.moveToFirst()){
            do{

                Ponto u = new Ponto();
                u.setId(c.getInt(0));
                u.setTecnico_id(c.getInt(1));
                u.setAcao_hora(c.getString(2));
                u.setData(c.getString(3));
                u.setHora(c.getString(4));
                u.setLatitude(c.getString(5));
                u.setLongitude(c.getString(6));
                u.setEnviado(c.getString(7));
                String aux1 = c.getString(8);
                String aux2 = c.getString(9);
                listPonto.add(u);
            }while (c.moveToNext());

        }
        db.close();
        return listPonto;
    }
    public void insertDespacho( Despacho despacho){

        ContentValues cv = new ContentValues();

        cv.put("atividade_id",despacho.getAtividade_id());
        cv.put("acao",despacho.getAcao());
        cv.put("tecnico_id",despacho.getTecnico_id());
        cv.put("acao_hora", despacho.getAcao_hora());
        cv.put("assinatura",despacho.getAssinatura());
        cv.put("data_encerramento",despacho.getData_enceramento());
        cv.put("reparo_efetuado",despacho.getReparo_efetuado());
        cv.put("observacao",despacho.getObservacao());
        cv.put("contato_local",despacho.getContato_local());
        //cv.put("contato",despacho.getContato_local());
        cv.put("documento",despacho.getDocumento());
        cv.put("assinatura_1",despacho.getAssinatura_1());
        cv.put("assinatura_2",despacho.getAssinatura_2());
        cv.put("latitude",despacho.getLatitude());
        cv.put("longitude",despacho.getLongitude());
        Despacho upAtiv = getDespachoById(despacho.getId());
        SQLiteDatabase db =  getWritableDatabase();

        if(upAtiv==null){
            db.insert("despachos", null, cv);
        }
        else {
            db.update("despachos", cv, " ID = ?", new String[]{(Integer.toString(upAtiv.getId()))});
        }
        db.close();
    }
    public ArrayList<Despacho> selectDespachos(){
        ArrayList<Despacho> listDespacho = new ArrayList<Despacho>();
        SQLiteDatabase db =  getReadableDatabase();
        String sql = " SELECT * FROM despachos";
        Cursor c = db.rawQuery(sql, null);
        if (c.moveToFirst()){
            do{

                Despacho u = new Despacho();
                u.setId(c.getInt(0));
                u.setAtividade_id(c.getInt(1));
                u.setAcao(c.getString(2));
                u.setTecnico_id(c.getInt(3));
                u.setAcao_hora(c.getString(4));
                u.setAssinatura(c.getString(5));
                u.setData_enceramento(c.getString(6));
                u.setReparo_efetuado(c.getString(7));
                u.setObservacao(c.getString(8));
                u.setContato_local(c.getString(9));
                u.setDocumento(c.getString(10));
                u.setAssinatura_1(c.getString(11));
                u.setAssinatura_2(c.getString(12));
                u.setLatitude(c.getString(13));
                u.setLongitude(c.getString(14));
                listDespacho.add(u);
            }while (c.moveToNext());

        }
        db.close();
        return listDespacho;
    }
    public Despacho getDespachoById(int id){
        SQLiteDatabase db =  getReadableDatabase();
        String sql = " SELECT * FROM despachos WHERE ID=" +id+"" ;
        Cursor c = db.rawQuery(sql, null);
        Despacho u = null;
        if (c.moveToFirst()){
            u = new Despacho();
            u.setId(c.getInt(0));
            u.setAtividade_id(c.getInt(1));
            u.setAcao(c.getString(2));
            u.setTecnico_id(c.getInt(3));
            u.setAcao_hora(c.getString(4));
            u.setAssinatura(c.getString(5));
            u.setData_enceramento(c.getString(6));
            u.setReparo_efetuado(c.getString(7));
            u.setObservacao(c.getString(8));
            u.setContato_local(c.getString(9));
            u.setDocumento(c.getString(10));
            u.setAssinatura_1(c.getString(11));
            u.setAssinatura_2(c.getString(12));
            u.setLatitude(c.getString(13));
            u.setLongitude(c.getString(14));
        }
        db.close();
        return u;
    }
    public Despacho getDespachoByAtividadeId(int atividade_id){
        SQLiteDatabase db =  getReadableDatabase();
        String sql = " SELECT * FROM DESPACHOS WHERE atividade_id=" +atividade_id+"" ;
        //String sql = " SELECT * FROM despachos " ;
        Cursor c = db.rawQuery(sql, null);
        Despacho u = null;
        if (c.moveToFirst()){
            u = new Despacho();
            u.setId(c.getInt(0));
            u.setAtividade_id(c.getInt(1));
            u.setAcao(c.getString(2));
            u.setTecnico_id(c.getInt(3));
            u.setAcao_hora(c.getString(4));
            u.setAssinatura(c.getString(5));
            u.setData_enceramento(c.getString(6));
            u.setReparo_efetuado(c.getString(7));
            u.setObservacao(c.getString(8));
            u.setContato_local(c.getString(9));
            u.setDocumento(c.getString(10));
            u.setAssinatura_1(c.getString(11));
            u.setAssinatura_2(c.getString(12));
            u.setLatitude(c.getString(13));
            u.setLongitude(c.getString(14));
        }
        db.close();
        return u;
    }
    public void insertAtividade( Atividade atividade){
        Atividade upAtiv = getAtividadeById(atividade.getId());
        ContentValues cv = new ContentValues();
        cv.put("id",atividade.getId());
        cv.put("mercado",atividade.getMercado());
        cv.put("nr_os",atividade.getNr_os());
        cv.put("tipo",atividade.getTipo());
        cv.put("abertura", atividade.getAbertura());
        cv.put("periodo",atividade.getPeriodo());
        cv.put("agendamento",atividade.getAgendamento());
        cv.put("solicitante",atividade.getSolicitante());
        cv.put("cliente",atividade.getCliente());
        cv.put("telefone",atividade.getTelefone());
        cv.put("celular",atividade.getCelular());
        cv.put("observacao_abertura",atividade.getObservacao_abertura());
        cv.put("atendente",atividade.getAtendente());
        cv.put("tipo_logr",atividade.getTipo_logr());
        cv.put("logradouro",atividade.getLogradouro());
        cv.put("numero",atividade.getNumero());
        cv.put("quadra",atividade.getQuadra());
        cv.put("complemento",atividade.getComplemento());
        cv.put("bairro",atividade.getBairro());
        cv.put("cidade",atividade.getCidade());
        cv.put("uf",atividade.getUf());
        cv.put("cep",atividade.getCep());
        cv.put("referencia",atividade.getReferencia());
        cv.put("latitude",atividade.getLatitude());
        cv.put("longitude",atividade.getLongitude());
        cv.put("ip",atividade.getIp());
        cv.put("mac",atividade.getMac());
        cv.put("login",atividade.getLogin());
        cv.put("senha",atividade.getSenha());
        cv.put("plano",atividade.getPlano());
        cv.put("km_inicio",atividade.getKm_inicio());
        cv.put("km_fim",atividade.getKm_fim());
        cv.put("antena",atividade.getAntena());
        cv.put("prop_equipamento",atividade.getProp_equipamento());
        cv.put("conexao",atividade.getConexao());
        cv.put("nr_cabo",atividade.getNr_cabo());
        cv.put("par_eletrico",atividade.getPar_eletrico());
        cv.put("servidor",atividade.getServidor());
        cv.put("ponto_acesso",atividade.getPonto_acesso());
        cv.put("defeito_reclamado",atividade.getDefeito_reclamado());
        cv.put("desiguinacao",atividade.getDesiguinacao());

        cv.put("pop",atividade.getPop());
        cv.put("interface_",atividade.getInterface_());
        cv.put("swit",atividade.getSwit());
        cv.put("porta",atividade.getPorta());
        cv.put("status_fechamento",atividade.getStatus_fechamento());
        cv.put("tecnico_id",atividade.getTecnico_id());
        cv.put("ordem" ,atividade.getOrdem());

        cv.put("lat_porta" ,atividade.getLat_porta());
        cv.put("long_porta" ,atividade.getLong_porta());
        cv.put("lat_switch" ,atividade.getLat_switch());
        cv.put("long_switch",atividade.getLong_switch());


        SQLiteDatabase db =  getWritableDatabase();

        if(upAtiv==null){
            db.insert("atividades", null, cv);
        }
        else {
            db.update("atividades", cv, " ID = ?", new String[]{(Integer.toString(upAtiv.getId()))});
        }
        db.close();
    }

    public ArrayList<Atividade> selectAtividadesFechadas(int user_id){
        ArrayList<Atividade> listAtividade = new ArrayList<Atividade>();
        SQLiteDatabase db =  getReadableDatabase();
        String conditions= " and 1 =1 ";

        String sql = " SELECT a.* FROM ATIVIDADES a where status_fechamento ='FECHADO' and a.tecnico_id = "+ user_id + conditions;
        //String sql = " SELECT a.* FROM ATIVIDADES a ";
        Cursor c = db.rawQuery(sql, null);
        if (c.moveToFirst()){
            do{

                Atividade u = new Atividade();
                u.setId(c.getInt(0));
                u.setMercado(c.getString(1));
                u.setNr_os(c.getString(2));
                u.setTipo(c.getString(3));
                u.setAbertura(c.getString(4));
                u.setPeriodo(c.getString(5));
                u.setAgendamento(c.getString(6));
                u.setSolicitante(c.getString(7));
                u.setCliente(c.getString(8));
                u.setTelefone(c.getString(9));
                u.setCelular(c.getString(10));
                u.setObservacao_abertura(c.getString(11));
                u.setAtendente(c.getString(12));
                u.setTipo_logr(c.getString(13));
                u.setLogradouro(c.getString(14));
                u.setNumero(c.getString(15));
                u.setQuadra(c.getString(16));
                u.setComplemento(c.getString(17));
                u.setBairro(c.getString(18));
                u.setCidade(c.getString(19));
                u.setUf(c.getString(20));
                u.setCep(c.getString(21));
                u.setReferencia(c.getString(22));
                u.setLatitude(c.getString(23));
                u.setLongitude(c.getString(24));
                u.setIp(c.getString(25));
                u.setMac(c.getString(26));
                u.setLogin(c.getString(27));
                u.setSenha(c.getString(28));
                u.setPlano(c.getString(29));
                u.setKm_inicio(c.getString(30));
                u.setKm_fim(c.getString(31));
                u.setAntena(c.getString(32));
                u.setProp_equipamento(c.getString(33));
                u.setConexao(c.getString(34));
                u.setNr_cabo(c.getString(35));
                u.setPar_eletrico(c.getString(36));
                u.setServidor(c.getString(37));
                u.setPonto_acesso(c.getString(38));
                u.setDefeito_reclamado(c.getString(39));
                u.setDesiguinacao(c.getString(40));
                u.setPop(c.getString(41));
                u.setInterface_(c.getString(42));
                u.setSwit(c.getString(43));
                u.setPorta(c.getString(44));
                u.setStatus_fechamento(c.getString(45));
                u.setTecnico_id(c.getInt(46));
                u.setOrdem(c.getInt(47));
                u.setLat_porta(c.getString(48));
                u.setLong_porta(c.getString(49));
                u.setLat_switch(c.getString(50));
                u.setLong_switch(c.getString(51));
                listAtividade.add(u);
            }while (c.moveToNext());

        }
        db.close();
        return listAtividade;
    }
    public ArrayList<Atividade> selectAtividades(int user_id, String newText){
        ArrayList<Atividade> listAtividade = new ArrayList<Atividade>();
        SQLiteDatabase db =  getReadableDatabase();
        String conditions= " and 1 =1 ";
        if (!newText.equals("") ){
            conditions=" and (a.cliente like '%"+newText+"%' or a.nr_os like '%"+newText+"%' or  a.tipo like '%"+newText+"%' or a.periodo like '%"+newText+"%' )";
        }
        String sql = " SELECT a.* FROM ATIVIDADES a where coalesce(status_fechamento,'')='' and a.tecnico_id = "+ user_id + conditions;
        //String sql = " SELECT a.* FROM ATIVIDADES a ";
        Cursor c = db.rawQuery(sql, null);
        if (c.moveToFirst()){
            do{

                Atividade u = new Atividade();
                u.setId(c.getInt(0));
                u.setMercado(c.getString(1));
                u.setNr_os(c.getString(2));
                u.setTipo(c.getString(3));
                u.setAbertura(c.getString(4));
                u.setPeriodo(c.getString(5));
                u.setAgendamento(c.getString(6));
                u.setSolicitante(c.getString(7));
                u.setCliente(c.getString(8));
                u.setTelefone(c.getString(9));
                u.setCelular(c.getString(10));
                u.setObservacao_abertura(c.getString(11));
                u.setAtendente(c.getString(12));
                u.setTipo_logr(c.getString(13));
                u.setLogradouro(c.getString(14));
                u.setNumero(c.getString(15));
                u.setQuadra(c.getString(16));
                u.setComplemento(c.getString(17));
                u.setBairro(c.getString(18));
                u.setCidade(c.getString(19));
                u.setUf(c.getString(20));
                u.setCep(c.getString(21));
                u.setReferencia(c.getString(22));
                u.setLatitude(c.getString(23));
                u.setLongitude(c.getString(24));
                u.setIp(c.getString(25));
                u.setMac(c.getString(26));
                u.setLogin(c.getString(27));
                u.setSenha(c.getString(28));
                u.setPlano(c.getString(29));
                u.setKm_inicio(c.getString(30));
                u.setKm_fim(c.getString(31));
                u.setAntena(c.getString(32));
                u.setProp_equipamento(c.getString(33));
                u.setConexao(c.getString(34));
                u.setNr_cabo(c.getString(35));
                u.setPar_eletrico(c.getString(36));
                u.setServidor(c.getString(37));
                u.setPonto_acesso(c.getString(38));
                u.setDefeito_reclamado(c.getString(39));
                u.setDesiguinacao(c.getString(40));
                u.setPop(c.getString(41));
                u.setInterface_(c.getString(42));
                u.setSwit(c.getString(43));
                u.setPorta(c.getString(44));
                u.setStatus_fechamento(c.getString(45));
                u.setTecnico_id(c.getInt(46));
                u.setOrdem(c.getInt(47));
                u.setLat_porta(c.getString(48));
                u.setLong_porta(c.getString(49));
                u.setLat_switch(c.getString(50));
                u.setLong_switch(c.getString(51));
                listAtividade.add(u);
            }while (c.moveToNext());

        }
        db.close();
        return listAtividade;
    }
    public Atividade getAtividadeById(int id){
        SQLiteDatabase db =  getReadableDatabase();
        String sql = " SELECT * FROM ATIVIDADES WHERE ID=" +id+"" ;
        Cursor c = db.rawQuery(sql, null);
        Atividade u = null;
        if (c.moveToFirst()){
            u = new Atividade();
            u.setId(c.getInt(0));
            u.setMercado(c.getString(1));
            u.setNr_os(c.getString(2));
            u.setTipo(c.getString(3));
            u.setAbertura(c.getString(4));
            u.setPeriodo(c.getString(5));
            u.setAgendamento(c.getString(6));
            u.setSolicitante(c.getString(7));
            u.setCliente(c.getString(8));
            u.setTelefone(c.getString(9));
            u.setCelular(c.getString(10));
            u.setObservacao_abertura(c.getString(11));
            u.setAtendente(c.getString(12));
            u.setTipo_logr(c.getString(13));
            u.setLogradouro(c.getString(14));
            u.setNumero(c.getString(15));
            u.setQuadra(c.getString(16));
            u.setComplemento(c.getString(17));
            u.setBairro(c.getString(18));
            u.setCidade(c.getString(19));
            u.setUf(c.getString(20));
            u.setCep(c.getString(21));
            u.setReferencia(c.getString(22));
            u.setLatitude(c.getString(23));
            u.setLongitude(c.getString(24));
            u.setIp(c.getString(25));
            u.setMac(c.getString(26));
            u.setLogin(c.getString(27));
            u.setSenha(c.getString(28));
            u.setPlano(c.getString(29));
            u.setKm_inicio(c.getString(30));
            u.setKm_fim(c.getString(31));
            u.setAntena(c.getString(32));
            u.setProp_equipamento(c.getString(33));
            u.setConexao(c.getString(34));
            u.setNr_cabo(c.getString(35));
            u.setPar_eletrico(c.getString(36));
            u.setServidor(c.getString(37));
            u.setPonto_acesso(c.getString(38));
            u.setDefeito_reclamado(c.getString(39));
            u.setDesiguinacao(c.getString(40));
            u.setPop(c.getString(41));
            u.setInterface_(c.getString(42));
            u.setSwit(c.getString(43));
            u.setPorta(c.getString(44));
            u.setStatus_fechamento(c.getString(45));
            u.setTecnico_id(c.getInt(46));
            u.setOrdem(c.getInt(47));
            u.setLat_porta(c.getString(48));
            u.setLong_porta(c.getString(49));
            u.setLat_switch(c.getString(50));
            u.setLong_switch(c.getString(51));
        }
        db.close();
        return u;
    }
    public Atividade getAtividadeByUserandId(int id, int tecnico_id){
        SQLiteDatabase db =  getReadableDatabase();
        String sql = " SELECT * FROM ATIVIDADES WHERE  ID=" +id+" and TECNICO_ID=" +tecnico_id+""  ;
        Cursor c = db.rawQuery(sql, null);
        Atividade u = null;
        if (c.moveToFirst()){
            u = new Atividade();
            u.setId(c.getInt(0));
            u.setMercado(c.getString(1));
            u.setNr_os(c.getString(2));
            u.setTipo(c.getString(3));
            u.setAbertura(c.getString(4));
            u.setPeriodo(c.getString(5));
            u.setAgendamento(c.getString(6));
            u.setSolicitante(c.getString(7));
            u.setCliente(c.getString(8));
            u.setTelefone(c.getString(9));
            u.setCelular(c.getString(10));
            u.setObservacao_abertura(c.getString(11));
            u.setAtendente(c.getString(12));
            u.setTipo_logr(c.getString(13));
            u.setLogradouro(c.getString(14));
            u.setNumero(c.getString(15));
            u.setQuadra(c.getString(16));
            u.setComplemento(c.getString(17));
            u.setBairro(c.getString(18));
            u.setCidade(c.getString(19));
            u.setUf(c.getString(20));
            u.setCep(c.getString(21));
            u.setReferencia(c.getString(22));
            u.setLatitude(c.getString(23));
            u.setLongitude(c.getString(24));
            u.setIp(c.getString(25));
            u.setMac(c.getString(26));
            u.setLogin(c.getString(27));
            u.setSenha(c.getString(28));
            u.setPlano(c.getString(29));
            u.setKm_inicio(c.getString(30));
            u.setKm_fim(c.getString(31));
            u.setAntena(c.getString(32));
            u.setProp_equipamento(c.getString(33));
            u.setConexao(c.getString(34));
            u.setNr_cabo(c.getString(35));
            u.setPar_eletrico(c.getString(36));
            u.setServidor(c.getString(37));
            u.setPonto_acesso(c.getString(38));
            u.setDefeito_reclamado(c.getString(39));
            u.setDesiguinacao(c.getString(40));
            u.setPop(c.getString(41));
            u.setInterface_(c.getString(42));
            u.setSwit(c.getString(43));
            u.setPorta(c.getString(44));
            u.setStatus_fechamento(c.getString(45));
            u.setTecnico_id(c.getInt(46));
            u.setOrdem(c.getInt(47));
            u.setLat_porta(c.getString(48));
            u.setLong_porta(c.getString(49));
            u.setLat_switch(c.getString(50));
            u.setLong_switch(c.getString(51));
        }
        db.close();
        return u;
    }

    public User insertUser( User user){

        ContentValues cv = new ContentValues();
        cv.put("external_id",user.getExternal_id());
        cv.put("nome",user.getNome());
        cv.put("email",user.getEmail());
        cv.put("senha",user.getSenha());
        cv.put("login", user.getLogin());
        cv.put("perfil", user.getPerfil());

        user.setIn_use(1);
        cv.put("in_use", user.getIn_use());

        User upUser = getUserByExternalId(user.getExternal_id());
        SQLiteDatabase db =  getWritableDatabase();
        String sqlUser = " update users set in_use = 0 ";
        db.execSQL(sqlUser);
        if(upUser==null){
            db.insert("users", null, cv);
        }
        else {
            if (! (user.getPath_assinatura() == null || user.getPath_assinatura().isEmpty()) ) {
                cv.put("path_assinatura", user.getPath_assinatura());
                cv.put("file_name_assinatura", user.getFile_name_assinatura());
            }
            db.update("users", cv, " ID = ?", new String[]{(Integer.toString(upUser.getId()))});
        }
        db.close();
        return getUserByExternalId(user.getExternal_id());
    }
    public void logoutUser(){

        SQLiteDatabase db =  getWritableDatabase();
        String sqlUser = " update users set in_use = 0 ";
        db.execSQL(sqlUser);
        db.close();
    }
    public List<User> selectUsers(){
        List<User> listUser = new ArrayList<User>();
        SQLiteDatabase db =  getReadableDatabase();
        String sql = " SELECT * FROM USERS";
        Cursor c = db.rawQuery(sql, null);
        if (c.moveToFirst()){
            do{
                User u = new User();
                u.setExternal_id(c.getInt(1));
                u.setNome(c.getString(2));
                u.setEmail(c.getString(3));
                u.setLogin(c.getString(4));
                u.setSenha(c.getString(5));
                u.setPath_assinatura(c.getString(6));
                u.setFile_name_assinatura(c.getString(7));
                u.setPerfil(c.getInt(8));
                listUser.add(u);
            }while (c.moveToNext());

        }
        db.close();
        return listUser;
    }

    public User getUserByExternalId(int external_id){
        SQLiteDatabase db =  getReadableDatabase();
        String sql = " SELECT * FROM USERS WHERE EXTERNAL_ID=" +external_id+"" ;
        Cursor c = db.rawQuery(sql, null);
        User u = null;
        if (c.moveToFirst()){
            u = new User();
            u.setId(c.getInt(0));
            u.setExternal_id(c.getInt(1));
            u.setNome(c.getString(2));
            u.setEmail(c.getString(3));
            u.setLogin(c.getString(4));
            u.setSenha(c.getString(5));
            u.setPath_assinatura(c.getString(6));
            u.setFile_name_assinatura(c.getString(7));
            u.setPerfil(c.getInt(8));
        }
        db.close();
        return u;
    }
    public User validaLogin(String login, String senha){
      //  SQLiteDatabase db2 =  getWritableDatabase();
      //  String sqlDropEquipes4 = " delete from RESPOSTAS";
       // db2.execSQL(sqlDropEquipes4);
       // db2.close();
        SQLiteDatabase db =  getReadableDatabase();
        String sql = " SELECT * FROM USERS WHERE upper(LOGIN)=upper('" +login+"') and senha ='"+senha+"'" ;
        Cursor c = db.rawQuery(sql, null);
        User u = null;
        if (c.moveToFirst()){
            u = new User();
            u.setId(c.getInt(0));
            u.setExternal_id(c.getInt(1));
            u.setNome(c.getString(2));
            u.setEmail(c.getString(3));
            u.setLogin(c.getString(4));
            u.setSenha(c.getString(5));
            u.setPath_assinatura(c.getString(6));
            u.setFile_name_assinatura(c.getString(7));
            u.setPerfil(c.getInt(8));
        }
        db.close();
        return u;
    }
    public User getLoggedUser(){
        //  SQLiteDatabase db2 =  getWritableDatabase();
        //  String sqlDropEquipes4 = " delete from RESPOSTAS";
        // db2.execSQL(sqlDropEquipes4);
        // db2.close();
        SQLiteDatabase db =  getReadableDatabase();
        String sql = " SELECT * FROM USERS where in_use = 1" ;
        Cursor c = db.rawQuery(sql, null);
        User u = null;
        if (c.moveToFirst()){
            u = new User();
            u.setId(c.getInt(0));
            u.setExternal_id(c.getInt(1));
            u.setNome(c.getString(2));
            u.setEmail(c.getString(3));
            u.setLogin(c.getString(4));
            u.setSenha(c.getString(5));
            u.setPath_assinatura(c.getString(6));
            u.setFile_name_assinatura(c.getString(7));
            u.setPerfil(c.getInt(8));
        }
        db.close();
        return u;
    }
}
