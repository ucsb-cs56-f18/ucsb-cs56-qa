package edu.ucsb.cs56.pconrad.springboot.hello;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
// import java.util.concurrent.TimeUnit;
import java.util.concurrent.CountDownLatch;
import java.io.FileInputStream;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DatabaseAPI {
    private static final String authFileName = "experimental-prj-firebase-adminsdk-9tti7-5577b956fb.json";

    // initialize database access
    static {
        try {
            FileInputStream serviceAccount =
                    new FileInputStream(DatabaseAPI.authFileName);

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://experimental-prj.firebaseio.com")
                    .build();

            FirebaseApp.initializeApp(options);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private static final FirebaseDatabase database = FirebaseDatabase.getInstance();


    // constructor is disabled
    private DatabaseAPI() {}

    // DONE
    // createUser()
    public static boolean createUser(User user) {
        // check if the userid already exists
        if (DatabaseAPI.findUser(user.getUserid()) != null) { return false; }
        DatabaseReference ref = DatabaseAPI.database.getReference("users");
        ref.child(user.getUserid()).setValueAsync(user);
        return true;
    }

    // DONE
    // findUser()
    public static User findUser(String userid) {
        DatabaseReference ref = DatabaseAPI.database.getReference("users").child(userid);
        CountDownLatch doneSignal = new CountDownLatch(1);
        List<User> temp = new ArrayList<>();

        System.out.println("Before read");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // System.out.println(dataSnapshot);
                User target = dataSnapshot.getValue(User.class);
                if (target != null)
                    temp.add(target);
                doneSignal.countDown();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
                doneSignal.countDown();
            }
        });
        System.out.println("End read");
        System.out.println("Begin wait");
        try {
            doneSignal.await();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        System.out.println("End wait");

        User target = null;
        if (temp.size() > 0)
            target = temp.get(0);
        return target;
    }

    // TEST
    // composeQuestion()
    public static boolean composeQuestion(Question q) {
        // TODO
        DatabaseReference qref = DatabaseAPI.database.getReference("questions");
        DatabaseReference uqlref = DatabaseAPI.database.getReference("user_question_lists");
        qref.child(q.getQid()).setValueAsync(q);
        uqlref.child(q.getAskerid()).child(q.getTimeCreate()).setValueAsync(q.getQid());
        return true;
    }

    // TEST
    // findQuestion
    public static Question findQuestion(String qid) {
        DatabaseReference ref = DatabaseAPI.database.getReference("questions").child(qid);
        CountDownLatch doneSignal = new CountDownLatch(1);
        List<Question> temp = new ArrayList<>();

        System.out.println("Before read");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Question target = dataSnapshot.getValue(Question.class);
                if (target != null)
                    temp.add(target);
                doneSignal.countDown();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
                doneSignal.countDown();
            }
        });
        System.out.println("End read");
        System.out.println("Begin wait");
        try {
            doneSignal.await();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        System.out.println("End wait");

        Question target = null;
        if (temp.size() > 0)
            target = temp.get(0);
        return target;
    }

    // TODO
    // retrieveUserQuestionList()
    public static List<String> retrieveUserQuestionList(String uid) {
        DatabaseReference ref = DatabaseAPI.database.getReference("user_question_lists").child(uid);
        CountDownLatch doneSignal = new CountDownLatch(1);
        List<String> qids = new ArrayList<>();

        System.out.println("Before read");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println(dataSnapshot);
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    String target = child.getValue(String.class);
                    System.out.println(target);
                    qids.add(target);
                }
                doneSignal.countDown();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
                doneSignal.countDown();
            }
        });
        System.out.println("End read");
        System.out.println("Begin wait");
        try {
            doneSignal.await();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        System.out.println("End wait");

        return qids;
    }


    // DEBUG
    // demo: read data from the database
    public static void readDataDemo() {
        final List<SampleUser> list = new ArrayList<>();
        String path = "sample/users";
        System.out.println("Before call");

        DatabaseAPI.readData(path, list);

        System.out.println("End call");
    }

    // DEBUG
    // demo: save data to the database
    public static void saveDataDemo() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("sample");
        DatabaseReference usersRef = ref.child("users");

        usersRef.child("alanisawesome").setValueAsync(new SampleUser("Alan Turing", "aturing@ucsb.edu"));
        usersRef.child("gracehop").setValueAsync(new SampleUser("Grace Hopper", "ghopper@ucsb.edu"));
        usersRef.child("test00").setValueAsync(new SampleUser("Test Object 00", "test00@ucsb.edu"));
    }

    // DEBUG
    // readData()
    public static void readData(String path, List<SampleUser> list) {
        DatabaseReference ref = DatabaseAPI.database.getReference(path);
        CountDownLatch doneSignal = new CountDownLatch(1);

        System.out.println("Before read");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // System.out.println(dataSnapshot);
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    SampleUser su = child.getValue(SampleUser.class);
                    // System.out.println(su);
                    list.add(su);
                }
                doneSignal.countDown();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
                doneSignal.countDown();
            }
        });
        System.out.println("End read");
        System.out.println("Begin wait");
        try {
            doneSignal.await();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        System.out.println("End wait");
        System.out.println(list);
    }

}
