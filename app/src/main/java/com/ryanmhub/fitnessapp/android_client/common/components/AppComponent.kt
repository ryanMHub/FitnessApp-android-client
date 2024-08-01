package com.ryanmhub.fitnessapp.android_client.common.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ryanmhub.fitnessapp.android_client.R
import com.ryanmhub.fitnessapp.android_client.ui.theme.*

@Composable
fun NormalTextComponent(value: String){
    Text(
        text = value,
        modifier = Modifier.fillMaxWidth().heightIn(min = 40.dp),
        style = TextStyle(
            fontSize = 24.sp,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal
        ),
        color = colorResource(id = R.color.colorText),
        textAlign = TextAlign.Center
    )
}

@Composable
fun UnderLinedTextComponent(value: String){
    Text(
        text = value,
        modifier = Modifier.fillMaxWidth().heightIn(min = 40.dp),
        style = TextStyle(
            fontSize = 24.sp,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal
        ),
        color = colorResource(id = R.color.colorGray),
        textAlign = TextAlign.Center,
        textDecoration = TextDecoration.Underline
    )
}

@Composable
fun HeaderTextComponent(value: String){
    Text(
        text = value,
        modifier = Modifier.fillMaxWidth().heightIn(),
        style = TextStyle(
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Normal
        ),
        color = colorResource(id = R.color.colorText),
        textAlign = TextAlign.Center
    )
}

//Todo: I'm not sure if .color() is the right approach here. Border color is not changing
@Composable
fun StandTextField(labelValue : String, painter: Painter, onTextChanged: (String) -> Unit, textValue : String){

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth().clip(componentShapes.small),
        label = {Text(text = labelValue)},
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Primary,
            unfocusedContainerColor = Primary,
            focusedLabelColor = Primary,
            cursorColor = Primary
        ),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        value = textValue,
        onValueChange = {
            onTextChanged(it)
        },
        leadingIcon = {
            Icon(painter = painter, contentDescription = "") //Get custom drawable.xml for icons
        },
        singleLine = true,
        maxLines = 1)

}

//Todo: I'm not sure if .color() is the right approach here. Border color is not changing
@Composable
fun PasswordTextField(labelValue : String, painter: Painter, onTextChanged : (String) -> Unit, textValue : String){
    val localFocusManager = LocalFocusManager.current

    val passwordVisible = remember {
        mutableStateOf(false)
    }

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth().clip(componentShapes.small),
        label = {Text(text = labelValue)},
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Primary,
            unfocusedContainerColor = Primary,
            focusedLabelColor = Primary,
            cursorColor = Primary
        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
        singleLine = true,
        keyboardActions = KeyboardActions(onDone = {
            localFocusManager.clearFocus()
        }),
        maxLines = 1,
        value = textValue,
        onValueChange = {
            onTextChanged(it)
        },
        leadingIcon = {
            Icon(painter = painter, contentDescription = "") //Get custom drawable.xml for icons
        },
        trailingIcon = {
            val iconImage = if(passwordVisible.value){
                Icons.Filled.Visibility
            } else {
                Icons.Filled.VisibilityOff
            }

            val description = if(passwordVisible.value){
                stringResource(R.string.hidePassword)
            } else {
                stringResource(R.string.showPassword)
            }

            IconButton(onClick = {passwordVisible.value = !passwordVisible.value}) {
                Icon(imageVector = iconImage, contentDescription = description)
            }
        },
        visualTransformation = if(passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation()
    )


}

@Composable
fun CheckBoxComponent(value : String, onTextSelected: (String) -> Unit){
    Row(
        modifier = Modifier.fillMaxWidth()
            .heightIn(56.dp)
            .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
    ) {
        val checkedState = remember {
            mutableStateOf(false)
        }
        Checkbox(checked = checkedState.value,
            onCheckedChange = {
                checkedState.value = !checkedState.value
            })

        ClickableTextComponent(value, onTextSelected)
    }
}

//Todo: I'm going to have to delete this or make it generic
@Composable
fun ClickableTextComponent(value : String, onTextSelected: (String) -> Unit){
    val initialText = "By continuing you accept our "
    val privacyPolicyText = "Privacy Policy"
    val andText = " and "
    val termsAndConditionsText = "Terms & Conditions"
    val annotatedString = buildAnnotatedString {
        append(initialText)
        withStyle(style = SpanStyle(color = Primary)) {
            pushStringAnnotation(tag = privacyPolicyText, annotation = privacyPolicyText)
            append(privacyPolicyText)
        }
        append(andText)
        withStyle(style = SpanStyle(color = Primary)) {
            pushStringAnnotation(tag = termsAndConditionsText, annotation = termsAndConditionsText)
            append(termsAndConditionsText)
        }

    }

    ClickableText(text = annotatedString, onClick = {offset ->
        annotatedString.getStringAnnotations(offset, offset)
            .firstOrNull()?.also{ span->
                Log.d("ClickableTextComponent", "{$span}")
                if(span.item == termsAndConditionsText || span.item == privacyPolicyText){
                    onTextSelected(span.item)
                }
            }
    })
}

//Todo: come up with a better approach for this component so that only one function is need instead of string specific
@Composable
fun ClickableTextComponentEnding(initialText : String, ending : String, onTextSelected: (String) -> Unit){
    val annotatedString = buildAnnotatedString {
        append(initialText)
        withStyle(style = SpanStyle(color = Primary)) {
            pushStringAnnotation(tag = ending, annotation = ending)
            append(ending)
        }
    }

    ClickableText(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 40.dp),
        style = TextStyle(
            fontSize = 21.sp,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal,
            textAlign = TextAlign.Center
        ),
        text = annotatedString, onClick = {offset ->
        annotatedString.getStringAnnotations(offset, offset)
            .firstOrNull()?.also{ span->
                Log.d("ClickableTextComponent", "{$span}")
                if(span.item == ending){
                    onTextSelected(span.item)
                }
            }
    })
}

@Composable
fun ButtonComponent(value : String, onButtonClicked: () -> Unit){
    Button(onClick = onButtonClicked,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(48.dp),
        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(Color.Transparent)
        ) {
        Box(modifier = Modifier.
            fillMaxWidth()
            .heightIn(48.dp).background(
                brush = Brush.horizontalGradient(listOf(Secondary, Primary)),
                shape = RoundedCornerShape(50.dp)
            ),
            contentAlignment = Alignment.Center
        ){
                Text(text = value,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun DividerTextComponent(){
    Row(modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ){
        Divider(modifier = Modifier
            .fillMaxWidth()
            .weight(1f),
        color = GrayColor,
        thickness = 1.dp)

        Text(modifier = Modifier.padding(10.dp),
            text = stringResource(R.string.or),
            fontSize = 20.sp,
            color = TextColor)

        Divider(modifier = Modifier
            .fillMaxWidth()
            .weight(1f),
            color = GrayColor,
            thickness = 1.dp)
    }
}


@Composable
fun PopUpComponent(title : String = "Default", message : String? = "Default", showAlert : MutableState<Boolean>){
    if(showAlert.value) {
        AlertDialog(onDismissRequest = { showAlert.value = false },
            title = { Text(text = title) },
            text = {
                if (message != null) {
                    Text(text = message)
                }
            },
            confirmButton = {
                Button(onClick = { showAlert.value = false }) {
                    Text(
                        text = stringResource(R.string.ok),
                        color = WhiteColor
                    )
                }
            }
        )
    }
}