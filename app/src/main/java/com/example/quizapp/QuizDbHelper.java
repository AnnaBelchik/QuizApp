package com.example.quizapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.quizapp.QuizContract.*;

import java.util.ArrayList;

public class QuizDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Quizzy.db";
    private static final int DATABASE_VERSION = 1;

    private SQLiteDatabase db;

    QuizDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        this.db = db;

        final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " +
                QuestionTable.TABLE_NAME + " ( " +
                QuestionTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionTable.COLUMN_QUESTION + " TEXT, " +
                QuestionTable.COLUMN_OPTION1 + " TEXT, " +
                QuestionTable.COLUMN_OPTION2 + " TEXT, " +
                QuestionTable.COLUMN_OPTION3 + " TEXT, " +
                QuestionTable.COLUMN_OPTION4 + " TEXT, " +
                QuestionTable.COLUMN_ANSWER_NR + " INTEGER" +
                ")";

        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);
        fillQuestionsTable();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + QuestionTable.TABLE_NAME);
        onCreate(db);

    }


    private void fillQuestionsTable() {

        Questions q1 = new Questions("Кому принадлежит выражение: Краткость - сестра таланта?", "Толстой", "Чехов", "Пушкин", "Лермонтов", 2);
        addQuestions(q1);
        Questions q2 = new Questions("Сколько лет было Анне Карениной на момент её гибели в произведении Л.Н.Толстого", "28 лет", "35 лет", "25 лет", "30 лет", 1);
        addQuestions(q2);
        Questions q3 = new Questions("Кто написал Курочку-Рябу", "Чехов", "Даль", "Агния Барто", "Эдуард Успенский", 2);
        addQuestions(q3);
        Questions q4 = new Questions("Что одиноко белело 'в тумане моря' в известном стихотворении М.Ю.Лермонтова", "Яхта", "Теплоход", "Айсберг", "Парус", 4);
        addQuestions(q4);
        Questions q5 = new Questions("Имя самой известной Родионовны?", "Анна", "Арина", "Светлана", "Нина", 2);
        addQuestions(q5);
        Questions q6 = new Questions("К какому жанру вы отнесете произведение Тургенева «Отцы и дети»?", "Сага", "Роман", "Повесть", "Пьеса",2);
        addQuestions(q6);
        Questions q7 = new Questions("Что, по мнению матери Булгакова является злом?", "Умение кривить душой", "Эгоизм", "Ложь", "Отсутвие веры", 2);
        addQuestions(q7);
        Questions q8 = new Questions("Какая настоящая фамилия Максима Горького?", "Лужин", "Алексеев", "Пешков", "Пязов", 3);
        addQuestions(q8);
        Questions q9 = new Questions("Кем по профессии был А.П.Чехов?", "Учитель", "Повар", "Юрист", "Врач", 4);
        addQuestions(q9);
        Questions q10 = new Questions("Сколько сыновей было у Тараса Бульбы?", "1", "2", "3", "4", 2);
        addQuestions(q10);
    }

    private void addQuestions(Questions question) {

        ContentValues cv = new ContentValues();
        cv.put(QuestionTable.COLUMN_QUESTION, question.getQuestion());
        cv.put(QuestionTable.COLUMN_OPTION1, question.getOption1());
        cv.put(QuestionTable.COLUMN_OPTION2, question.getOption2());
        cv.put(QuestionTable.COLUMN_OPTION3, question.getOption3());
        cv.put(QuestionTable.COLUMN_OPTION4, question.getOption4());
        cv.put(QuestionTable.COLUMN_ANSWER_NR, question.getAnswerNr());
        db.insert(QuestionTable.TABLE_NAME, null, cv);

    }

    public ArrayList<Questions> getAllQuestions() {

        ArrayList<Questions> questionList = new ArrayList<>();
        db = getReadableDatabase();


        String Projection[] = {

                QuestionTable._ID,
                QuestionTable.COLUMN_QUESTION,
                QuestionTable.COLUMN_OPTION1,
                QuestionTable.COLUMN_OPTION2,
                QuestionTable.COLUMN_OPTION3,
                QuestionTable.COLUMN_OPTION4,
                QuestionTable.COLUMN_ANSWER_NR
        };


        Cursor c = db.query(QuestionTable.TABLE_NAME,
                Projection,
                null,
                null,
                null,
                null,
                null);


        if (c.moveToFirst()) {
            do {

                Questions question = new Questions();
                question.setQuestion(c.getString(c.getColumnIndex(QuestionTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionTable.COLUMN_OPTION3)));
                question.setOption4(c.getString(c.getColumnIndex(QuestionTable.COLUMN_OPTION4)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuestionTable.COLUMN_ANSWER_NR)));

                questionList.add(question);

            } while (c.moveToNext());

        }
        c.close();
        return questionList;

    }

}


