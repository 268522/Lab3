package com.example.lab3

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lab3.ui.theme.Lab3Theme
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : ComponentActivity() {
    @SuppressLint("UnrememberedMutableState", "UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Lab3Theme{
                FirebaseUI(LocalContext.current)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FirebaseUI(context: Context) {

    val nazwa = remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally)
    {
        TextField(
            value = nazwa.value,
            onValueChange = { nazwa.value = it },
            placeholder = { Text(text = "Wpisz cos") },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),
            singleLine = true)

        Spacer(modifier = Modifier.height(10.dp))


        Button(onClick =
        {
            if (TextUtils.isEmpty(nazwa.value))
            {
                Toast.makeText(context, "Wpisz cos", Toast.LENGTH_SHORT).show()
            } else
            {
                addDataToFirebase(nazwa.value, context)
            }
        },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
        {
            Text(text = "Zaladuj dane", modifier = Modifier.padding(8.dp))
        }
    }
}

fun addDataToFirebase(
    nazwa: String,
    context: Context
) {
    val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    val dbCourses: CollectionReference = db.collection("Pakiet")
    val courses = Course(nazwa)
    dbCourses.add(courses).addOnSuccessListener {
        Toast.makeText(
            context,
            "Dodano",
            Toast.LENGTH_SHORT
        ).show()
    }
}