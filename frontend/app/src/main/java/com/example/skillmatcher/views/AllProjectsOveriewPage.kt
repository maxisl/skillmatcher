package com.example.skillmatcher

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.TabRowDefaults.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.skillmatcher.destinations.LandingPageDestination
import com.example.skillmatcher.destinations.VisitorsProjectOverviewPageDestination
import com.example.skillmatcher.ui.theme.Black
import com.example.skillmatcher.ui.theme.Green40
import com.example.skillmatcher.ui.theme.LMUGreen
import com.example.skillmatcher.ui.theme.White
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch
import java.time.format.TextStyle


@Preview
@Destination
@Composable
fun AllProjectsOverViewPage(
) {

    Column(modifier = Modifier.fillMaxSize().background(Color(Black.value))) {
        HeadBar(
            name = "Create Project", modifier = Modifier
                .padding(5.dp)
        )
        Divider(color= Color(White.value), thickness = 1.dp)
        Spacer(modifier = Modifier.height(4.dp))
        ProjectDetailsHeader()

    }
}
@Composable
fun HeadBar(
    name:String,
    modifier: Modifier=Modifier
){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,
        modifier =modifier.fillMaxWidth()
    ){
        androidx.compose.material3.Text(
            text = name,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            color = Color(LMUGreen.value)
        )

    }
}

@Composable
fun ProjectDetails(
    name:String,
    description:String,
    modifier: Modifier=Modifier
){  Column(
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = modifier
    ){
        Spacer(modifier = Modifier.height(4.dp))
        androidx.compose.material3.Text(
            text = name,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            color = Color(LMUGreen.value),
        )
    }
    Column(verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ){
        androidx.compose.material3.Text(
            text = description,
            fontSize = 25.sp,
            color = Color(White.value),
        )
    }
}


@Composable
fun ProjectDetailsHeader(modifier: Modifier=Modifier){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = modifier.fillMaxWidth(2f)
    ){
        //hier muss die Datenbankverknüpfung her
        ProjectDetails("Project 1", "Datenbanksystem mit SQL entwickeln")

    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = modifier.fillMaxWidth(2f)
    ){
        //hier muss die Datenbankverknüpfung her
        ProjectDetails("Project 2", "Frontend mit Compose entwickeln")
        Divider(color= Color.Black, thickness = 1.dp)

    }
}


//TODO wenn wir die Projecte haben
/**@Composable
fun AllProjectList(
    projects: List<Project>,
    modifier: Modifier =Modifier
){
    Box(modifier){
        val listState= rememberLazyListState()
        val scope= rememberCoroutineScope()
        LazyColumn(state= listState,
            contentPadding= PaddingValues(bottom=80.dp),
            modifier=Modifier.fillMaxWidth()){
            val grouped= projects.groupBy { it.firstName[0] }

            grouped.forEach{initial, projects ->
                stickyHeader{CharacterHeader(char= initial, Modifier.fillParentMaxWidth())}
           items(projects) { project ->
               ProjectListItem(
                   project = project,
                   modifier = Modifier.fillMaxWidth()
               )
                 }
            }
        }
        val showButton=listState.firstVisibleItemIndex >0
        AnimatedVisibility(visible = showButton,
        enter= fadeIn(),
            exit= fadeOut(),
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            ScrollToTopButton(onClick={
                                      scope.launch {
                                          listState.scrollToItem(0)
                                      }
            }, modifier=Modifier.padding(16.dp))
        }

    }
}

@Composable
fun CharacterHeader(char: Unit, fillParentMaxWidth: Modifier) {

}

@Composable
fun ProjectListItem(project: Project, modifier: Modifier) {

}

class Project {

    val firstName: Any
}
*/

