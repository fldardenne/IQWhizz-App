package com.example.iqwhizz;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.iqwhizz.DAO.ChallengeDAO;
import com.example.iqwhizz.DAO.FriendshipDAO;
import com.example.iqwhizz.DAO.TestDAO;
import com.example.iqwhizz.DAO.UserDAO;
import com.example.iqwhizz.Objects.Friendship;
import com.example.iqwhizz.Objects.Test;
import com.example.iqwhizz.Objects.User;

import java.util.List;

import static com.example.iqwhizz.DAO.TestDAO.getPossibleCategories;

public class ChallengeInit extends AppCompatActivity {

    Spinner category;
    Spinner type;
    Switch switch_friend;
    AutoCompleteTextView username_friend;
    Button play;
    TextView errormessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_init);

        category = findViewById(R.id.category);
        type = findViewById(R.id.type);
        switch_friend = findViewById(R.id.switch_friend);
        username_friend = findViewById(R.id.friend_username);

        List<String> friendsArray = Friendship.getFriendsUsername(User.currentUser.getUsername());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, friendsArray);
        username_friend.setAdapter(adapter);


        play = findViewById(R.id.new_challenge);
        username_friend.setInputType(InputType.TYPE_NULL);
        username_friend.setFocusable(false);
        username_friend.setVisibility(View.INVISIBLE);

        String[] items2 = new String[]{"Court (5 questions)", "Long (40 questions)"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items2);
        type.setAdapter(adapter2);




        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                onTypeSpinnerChange();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                return;
            }

        });


        errormessage = findViewById(R.id.error_message_chall);

        switch_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( switch_friend.isChecked()){
                    username_friend.setInputType(InputType.TYPE_CLASS_TEXT);
                    username_friend.setFocusableInTouchMode(true);
                    username_friend.setVisibility(View.VISIBLE);
                }else{
                    username_friend.setInputType(InputType.TYPE_NULL);
                    username_friend.setFocusable(false);
                    username_friend.setText("");
                    username_friend.setVisibility(View.INVISIBLE);
                }
            }
        });
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play_game();
            }
        });
    }

    private void onTypeSpinnerChange(){
        int nbQuestions = (type.getSelectedItem().toString().equals("Court (5 questions)")) ? 5 : 40;
        String[] items = TestDAO.getPossibleCategories(nbQuestions);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        errormessage.setText("");
        play.setClickable(true);
        if(items.length <= 0){
            play.setClickable(false);
            errormessage.setText("Aucune catégorie ne comporte assez de questions pour ce type de test");
        }

        category.setAdapter(adapter);
    }


    private void play_game() {

        if(switch_friend.isChecked()) {
            if (!UserDAO.userExists(username_friend.getText().toString())) {
                errormessage.setText("L'utilisateur n'existe pas");
            } else {
                String currentUser = User.currentUser.getUsername();
                String friend = username_friend.getText().toString();
                if (FriendshipDAO.isAcceptedFriend(currentUser, friend)) {
                    startGame(friend);
                } else {
                    int duration = Toast.LENGTH_SHORT;
                    Context context = getApplicationContext();
                    Toast toast = Toast.makeText(context, "Cet utilisateur n'est pas dans vos amis !", duration);
                    errormessage.setText("Cet utilisateur n'est pas dans vos amis !");
                    toast.show();
                }
            }
        }
        else {
            startGame(null);
        }
    }

    private void startGame(String friend) {
        Intent challengeIntent = new Intent(this, Challenge.class);

        int duration = Toast.LENGTH_SHORT;
        Context context = getApplicationContext();
        Toast toast = Toast.makeText(context, "Bon jeu !", duration);
        toast.show();
        String category_str = category.getSelectedItem().toString();
        String type_str = (type.getSelectedItem().toString().equals("Court (5 questions)")) ? "court" : "long";

        int testID = TestDAO.generateTest(category_str, type_str);
        if (friend != null) {
            ChallengeDAO.newChallenge(User.currentUser.getUsername(), friend, testID);
        }
        challengeIntent.putExtra("testID", testID);
        errormessage.setText("");
        startActivity(challengeIntent);
        this.finish();
    }
}
