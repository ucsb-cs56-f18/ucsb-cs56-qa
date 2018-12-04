package org.pac4j.demo.spring;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.CountDownLatch;
import java.io.FileInputStream;

import static java.util.concurrent.TimeUnit.SECONDS;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DatabaseAPI {
    private static final String authFileName = "experimental-prj-firebase-adminsdk.json";

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

    // TODO
    // isValueUser()
    public static boolean isValueUser(String uid) {
        if (uid == null) { return false; }
        User user = DatabaseAPI.findUser(uid);
        return (user != null);
    }

    // TODO
    // userExists()
    public static boolean userExists(String uid) {
        if (uid == null || uid.equals("")) { return false; }
        User user = DatabaseAPI.findUser(uid);
        return (user != null);
    }

    // DONE
    // requestLogin()
    public static boolean requestLogin(User user) {
        User corrus = DatabaseAPI.findUser(user.getUserid());
        if (corrus == null) { return false; }
        String corrpw = corrus.getPassword();
        return user.getPassword().compareTo(corrpw) == 0;
    }

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
        if (userid.equals("")) { return null; }
        DatabaseReference ref = DatabaseAPI.database.getReference("users").child(userid);
        CountDownLatch doneSignal = new CountDownLatch(1);
        List<User> temp = new ArrayList<>();

        // System.out.println("Before read");
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
        // System.out.println("End read");
        // System.out.println("Begin wait");
        try {
            doneSignal.await(45, TimeUnit.SECONDS);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        // System.out.println("End wait");

        User target = null;
        if (temp.size() > 0)
            target = temp.get(0);
        return target;
    }

    // DONE
    // composeQuestion()
    public static boolean composeQuestion(Question q) {
        DatabaseReference qref = DatabaseAPI.database.getReference("questions");
        DatabaseReference uqlref = DatabaseAPI.database.getReference("user_question_lists");

        qref.child(q.getQid()).setValueAsync(q);
        uqlref.child(q.getAskerid()).child(q.getTimeCreate()).setValueAsync(q.getQid());

        return true;
    }

    // DONE
    // findQuestion()
    public static Question findQuestion(String qid) {
        if (qid.equals("")) { return null; }
        DatabaseReference ref = DatabaseAPI.database.getReference("questions").child(qid);
        CountDownLatch doneSignal = new CountDownLatch(1);
        List<Question> temp = new ArrayList<>();

        // System.out.println("Before read");
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
        // System.out.println("End read");
        // System.out.println("Begin wait");
        try {
            doneSignal.await(45, TimeUnit.SECONDS);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        // System.out.println("End wait");

        Question target = null;
        if (temp.size() > 0)
            target = temp.get(0);
        return target;
    }

    // DONE
    // retrieveUserQuestionList()
    public static List<String> retrieveUserQuestionList(String uid) {
        if (uid.equals("")) { return null; }
        DatabaseReference ref = DatabaseAPI.database.getReference("user_question_lists").child(uid);
        CountDownLatch doneSignal = new CountDownLatch(1);
        List<String> qids = new ArrayList<>();

        // System.out.println("Before read");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // System.out.println(dataSnapshot);
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    String target = child.getValue(String.class);
                    // System.out.println(target);
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
        // System.out.println("End read");
        // System.out.println("Begin wait");
        try {
            doneSignal.await(45, TimeUnit.SECONDS);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        // System.out.println("End wait");

        return qids;
    }

    // DONE
    // composeAnswer()
    public static boolean composeAnswer(Answer a) {
        DatabaseReference aref = DatabaseAPI.database.getReference("answers");
        DatabaseReference ualref = DatabaseAPI.database.getReference("user_answer_lists");
        DatabaseReference qalref = DatabaseAPI.database.getReference("question_answer_lists");

        aref.child(a.getAid()).setValueAsync(a);
        ualref.child(a.getAnswererid()).child(a.getTimeCreate()).setValueAsync(a.getAid());
        qalref.child(a.getQid()).child(a.getTimeCreate()).setValueAsync(a.getAid());

        return true;
    }

    // DONE
    // findAnswer()
    public static Answer findAnswer(String aid) {
        if (aid.equals("")) { return null; }
        DatabaseReference ref = DatabaseAPI.database.getReference("answers").child(aid);
        CountDownLatch doneSignal = new CountDownLatch(1);
        List<Answer> temp = new ArrayList<>();

        // System.out.println("Before read");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Answer target = dataSnapshot.getValue(Answer.class);
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
        // System.out.println("End read");
        // System.out.println("Begin wait");
        try {
            doneSignal.await(45, TimeUnit.SECONDS);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        // System.out.println("End wait");

        Answer target = null;
        if (temp.size() > 0)
            target = temp.get(0);
        return target;
    }

    // DONE
    // retrieveUserAnswerList()
    public static List<String> retrieveUserAnswerList(String uid) {
        if (uid.equals("")) { return null; }
        DatabaseReference ref = DatabaseAPI.database.getReference("user_answer_lists").child(uid);
        CountDownLatch doneSignal = new CountDownLatch(1);
        List<String> aids = new ArrayList<>();

        // System.out.println("Before read");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // System.out.println(dataSnapshot);
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    String target = child.getValue(String.class);
                    // System.out.println(target);
                    aids.add(target);
                }
                doneSignal.countDown();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
                doneSignal.countDown();
            }
        });
        // System.out.println("End read");
        // System.out.println("Begin wait");
        try {
            doneSignal.await(45, TimeUnit.SECONDS);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        // System.out.println("End wait");

        return aids;
    }

    // DONE
    // retrieveQuestionAnswerList()
    public static List<String> retrieveQuestionAnswerList(String qid) {
        if (qid.equals("")) { return null; }
        DatabaseReference ref = DatabaseAPI.database.getReference("question_answer_lists").child(qid);
        CountDownLatch doneSignal = new CountDownLatch(1);
        List<String> aids = new ArrayList<>();

        // System.out.println("Before read");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // System.out.println(dataSnapshot);
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    String target = child.getValue(String.class);
                    // System.out.println(target);
                    aids.add(target);
                }
                doneSignal.countDown();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
                doneSignal.countDown();
            }
        });
        // System.out.println("End read");
        // System.out.println("Begin wait");
        try {
            doneSignal.await(45, TimeUnit.SECONDS);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        // System.out.println("End wait");

        return aids;
    }

    // DONE
    // retrieveQuestionList()
    public static List<Question> retrieveQuestionList() {
        DatabaseReference ref = DatabaseAPI.database.getReference("questions");
        List<Question> questionList = new ArrayList<>();
        CountDownLatch doneSignal = new CountDownLatch(1);

        // System.out.println("Before read");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // System.out.println(dataSnapshot);
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Question target = child.getValue(Question.class);
                    // System.out.println(target);
                    questionList.add(target);
                }
                doneSignal.countDown();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
                doneSignal.countDown();
            }
        });
        // System.out.println("End read");
        // System.out.println("Begin wait");
        try {
            doneSignal.await(45, TimeUnit.SECONDS);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        // System.out.println("End wait");

        Collections.reverse(questionList);
        return questionList;
    }

    // constructor is disabled
    private DatabaseAPI() {}
}
