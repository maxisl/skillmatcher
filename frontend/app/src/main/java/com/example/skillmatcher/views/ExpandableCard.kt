package com.example.skillmatcher
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.skillmatcher.AllProjectsOverViewPage
import androidx.compose.runtime.*
import androidx.compose.ui.unit.TextUnit
import com.ramcosta.composedestinations.annotation.Destination

@OptIn(ExperimentalMaterialApi::class)
@Destination
@Composable
fun ExpandableCard(
    title:String,
    titleFontSize: TextUnit =MaterialTheme.typography.h6.fontSize,
    titleFontWeight: FontWeight=FontWeight.Bold,
    description: String,
    descriptionFontSize:TextUnit = MaterialTheme.typography.subtitle1.fontSize,
    descriptionFontWeight:FontWeight =FontWeight.Normal,
    descriptionMaxLines: Int=4

) {
    var expandedState by remember { mutableStateOf(false) }
    val rotationState by animateFloatAsState(targetValue = if(expandedState) 180f else 0f)

    Card(modifier = Modifier
        .fillMaxWidth()
        .animateContentSize(
            animationSpec = tween(
                durationMillis = 300,
                easing = LinearOutSlowInEasing,

                )
        ),
        shape = RoundedCornerShape(4.dp),
        onClick = {
            expandedState =!expandedState
        }
    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)) {
            Row (verticalAlignment = Alignment.CenterVertically){
                Text(
                    modifier = Modifier.weight(6f),
                    text=title,
                    fontSize= titleFontSize,
                    fontWeight = titleFontWeight,
                    maxLines=1,
                    overflow= TextOverflow.Ellipsis
                )
                IconButton(
                    modifier= Modifier
                        .alpha(ContentAlpha.medium)
                        .weight(1f)
                        .rotate(rotationState),
                    onClick = {
                        expandedState=!expandedState
                    }){
                    Icon(imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Drop-Down Arrow" )
                }
            }
            if(expandedState){
                Text(text=description,
                    fontSize = descriptionFontSize,
                    fontWeight = descriptionFontWeight,
                    maxLines=descriptionMaxLines,
                    overflow= TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
@Preview
fun ExpandableCardPreview(){

}

