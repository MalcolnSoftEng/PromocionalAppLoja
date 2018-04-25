package syte.femininapv3.webserviceNovidades;


import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.util.concurrent.TimeUnit;

import syte.femininapv3.model.NovidadesItem;

public class NovidadesHttp {
    public static final String BASE_URL = "xxxxxxxxxxxxxxxxxxxp";


    public static NovidadesItem[] obterNovidadesDoServidor(){
        OkHttpClient client = new OkHttpClient();
        client.setReadTimeout(5, TimeUnit.SECONDS);
        client.setConnectTimeout(10, TimeUnit.SECONDS);
        Request request = new Request.Builder()
                .url(BASE_URL)
                .build();
        try {

            Response response = client.newCall(request).execute();
            String json = response.body().string();
            Gson gson = new Gson();
            return gson.fromJson(json,NovidadesItem[].class);
        } catch (Exception e){
            e.printStackTrace();
        }


        return null;
    }

}




        /*extends AppCompatActivity {

    private String TAG = NovidadesHttp.class.getSimpleName();

    EditText mEdtLocal; //Caixa de Texto para digitar a procura
    ImageButton mBtnBuscar; // botão(imagem) da Procura

    private ProgressDialog pDialog;
    public ListView lv;
    public static String lat,lng,localName;
    public String writeConsult;
    // URL from API to get JSON data
    private static String url;
    boolean resultSearch = true;
    String avatar;

    ArrayList<HashMap<String, String>> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coordinator_layout);

        userList = new ArrayList<>();

        lv = (ListView) findViewById(R.id.list);


        //mEdtLocal = (EditText) findViewById(R.id.edtLocal);
       // mBtnBuscar = (ImageButton) findViewById(R.id.imgBtnBuscar);
        url = "http://femenina.syte.com.br/webservice.php";
        new GetSearch().execute();
    }

    private class GetSearch extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(NovidadesHttp.this);
            pDialog.setMessage("Carregando...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Resposta da URL: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    // Getting JSON Array node
                    JSONArray contacts = jsonObj.getJSONArray("data");


                    // looping through All Contacts
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);

                        String id = c.getString("id");
                        String name = c.getString("nome");
                        String last_name = c.getString("descricao");
                        String avatar = c.getString("foto");
                        String price = c.getString("preco");

                        // tmp hash map for single contact
                        HashMap<String, String> contact = new HashMap<>();

                        // adding each child node to HashMap key => value
                        contact.put("id", id);
                        contact.put("nome", name);
                        contact.put("descricao", last_name);
                        contact.put("foto", avatar);
                        contact.put("preco", price);

                        // adding contact to contact list
                        userList.add(contact);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Erro : " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Erro: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Não foi possível estabelecer a conexão.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Não foi possível estabelecer a conexão. Check seu LogCAt para possíveis erros!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
        /*
            NovidadesListAdapter adapter = new NovidadesListAdapter(
                    NovidadesHttp.this, userList,
                    R.layout.list_item, new String[]{}, new int[]{});


            lv.setAdapter(adapter); */