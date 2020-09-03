package com.bignerdranch.android.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: ImageButton
    private lateinit var questionTextView: TextView
    private lateinit var previousButton : ImageButton

    private val questionBank = listOf(
        Question(R.string.question_australia,true),
        Question(R.string.question_oceans,true),
        Question(R.string.question_mideast, true),
        Question(R.string.question_africa, true),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )
    private var currentIndex = 0
    private var score = 0
    private var questionAnswered = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "Current question index: $currentIndex")

        try {
            val question = questionBank[currentIndex]
        } catch (ex: ArrayIndexOutOfBoundsException) {
            // Log a message at ERROR log level, along with an exception stack trace
            Log.e(TAG, "Index was out of bounds", ex)
        }
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        setContentView(R.layout.activity_main)
        previousButton = findViewById(R.id.previous_button)
        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        questionTextView = findViewById(R.id.question_text_view)

        trueButton.setOnClickListener { view: View? ->
            checkAnswer(true)
            score += 1
            trueButton.isEnabled= false;
            falseButton.isEnabled = false;
            questionAnswered += 1
        }
        falseButton.setOnClickListener { view: View? ->
            checkAnswer(false)
            trueButton.isEnabled = false;
            falseButton.isEnabled = false;
            questionAnswered += 1
        }
        nextButton.setOnClickListener {
            currentIndex = (currentIndex + 1) % questionBank.size
            updateQuestion()
 /*           if(questionAnswered == questionBank.size-1)
            {
                trueButton.isEnabled = false;
                falseButton.isEnabled = false;
               val toast = Toast.makeText(this, "Score: " + score / questionBank.size * 100 + "%", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.TOP, 0,200)
                toast.show()


            }
            if(currentIndex == 0)
            {
                questionAnswered = 0
                score = 0
            }

   */
        }
        questionTextView.setOnClickListener { view: View? ->
            currentIndex = (currentIndex + 1) % questionBank.size
            updateQuestion()
        }
        previousButton.setOnClickListener {
            if(currentIndex == 0)
            {
                currentIndex = questionBank.size-1
                updateQuestion()
            }
            else
            {
                currentIndex = (currentIndex - 1) % questionBank.size
                updateQuestion()
            }
        }
        updateQuestion()

    }
    override fun onStart()
    {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }
    override fun onResume()
    {
        super.onResume()
        Log.d(TAG,"onResume() called")
    }
    override fun onStop()
    {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }
    override fun onDestroy()
    {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }
    private fun updateQuestion(){
        val questionTextResId = questionBank[currentIndex].textResId
        questionTextView.setText(questionTextResId)
        trueButton.isEnabled = true;
        falseButton.isEnabled = true;


    }
    private fun checkAnswer(userAnswer: Boolean)
    {
        val correctAnswer = questionBank[currentIndex].answer

        val messageResId = if(userAnswer == correctAnswer)
        {
            R.string.correct_toast
        }
        else
        {
            R.string.incorrect_toast
        }
        val toast = Toast.makeText(this, messageResId, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.TOP, 0,200)
        toast.show()
    }
}