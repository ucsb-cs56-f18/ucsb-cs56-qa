# Q&A Webapp

# Fall 2018
* Raul: rosterz
* Kevin: tgwang1023
* Ken: ChangweiXu
* Jacob: jacobazamora
* Jonathan: jxu495
* Laura: yifanyu123

The main idea of the QA app is to provide UCSB students with a new form of asking questions around UCSB. The QA app will
allow students to submit questions so that other students who are (geographically) near by have to oppurtunity to answer their questions
and earn points.

Link to Website: https://ucsb-cs56-qa.herokuapp.com/


# How to set up connection to Firebase from your Webapp

1. Go to [Firebase Console](https://console.firebase.google.com/) and set up a new project.
2. Select the new project and go to "Project Settings" -> "Service Accounts" -> "Firebase Admin SDK", copy the Java version of Admin SDK configuration snippet, and click "Generate new private key".
<<<<<<< HEAD
3. Rename the downloaded .json file to `firebase-auth.json` and move it to `src/main/resource/static/auth/` directory.
4. Modify `static` field in `DatabaseAPI.java` with copied contents:
```
// You can copy these code
    private static final String authFileName = "target/classes/static/auth/firebase-auth.json";

    // initialize database access
    static {
        try {
            FileInputStream serviceAccount =
                    new FileInputStream(DatabaseAPI.authFileName);

            FirebaseOptions options = new FirebaseOptions.Builder()
              .setCredentials(GoogleCredentials.fromStream(serviceAccount))
              .setDatabaseUrl("<Your database address provided by firebase>")
              .build();

            FirebaseApp.initializeApp(options);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
=======
3. Rename the downloaded json file to `experimental-prj-firebase-adminsdk.json` and move it to `src/edu/static/auth/` directory.
4. Modify `static` field in `DatabaseAPI.java` with copied contents:
```
// You can copy these code
static {
  FileInputStream serviceAccount =
    new FileInputStream("path/to/serviceAccountKey.json");

  FirebaseOptions options = new FirebaseOptions.Builder()
    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
    .setDatabaseUrl("https://<your-project-name>.firebaseio.com")
    .build();

  FirebaseApp.initializeApp(options);
}
>>>>>>> 35ceca0e3effba255d61154ec95a6d0629a955d2
```
5. Save the change and now you can use `mvn spring-boot:run` to test the webapp in `http://localhost:8080/`.

**WARNING: Keep your private key safe and NEVER expose it to the public.**
