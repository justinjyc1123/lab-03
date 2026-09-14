package com.example.listycity3

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.listycity3.ui.theme.ListyCity3Theme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.FloatingActionButton
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.clickable



@Composable
fun CityListScreen(
    cities: List<City>,
    onAddCity:(City)->Unit,
    updateCity:(City, City) -> Unit,
    modifier: Modifier = Modifier
) {
    var newCityName by remember { mutableStateOf("") }
    var newProvinceName by remember { mutableStateOf("") }
    var showAddCityFields by remember { mutableStateOf(false) }
    var selectedCity by remember { mutableStateOf<City?>(null) }
    Column(modifier = modifier.fillMaxSize()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            FloatingActionButton(
                modifier = Modifier.padding((16.dp)),
                onClick = {
                    showAddCityFields = !showAddCityFields
                }
            ) {
                Text("+")
            }
        }

        if(showAddCityFields) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                OutlinedTextField(
                    value = newCityName,
                    onValueChange = { newCityName = it },
                    label = { Text("City") },
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                OutlinedTextField(
                    value = newProvinceName,
                    onValueChange = { newProvinceName = it },
                    label = { Text("Province") },
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    modifier = Modifier.padding(vertical = 12.dp),
                    onClick = {
                        if (newCityName.isNotBlank() && newProvinceName.isNotBlank()) {
                            onAddCity(
                                City(
                                    name = newCityName,
                                    province = newProvinceName
                                )
                            )
                            newCityName = ""
                            newProvinceName = ""
                            showAddCityFields = false
                        }
                    }
                ) {
                    Text("Add City")
                }
            }
        }
        selectedCity?.let { oldcity ->
            EditCity(
                oldCity = oldcity,
                onSave = {updateCity ->
                    updateCity(oldcity,updateCity)
                    selectedCity = null
                }
                ) }
        LazyColumn(modifier = modifier.fillMaxSize()) {
            itemsIndexed(cities) { index, city ->
                CityRow(city = city,
                    onClick = {selectedCity =city},)
                if (index < cities.lastIndex) {
                    HorizontalDivider()
                }
            }
        }

    }
}

@Composable
fun EditCity(
    oldCity: City,
    onSave:(City)-> Unit
){
    var editedName by remember(oldCity) { mutableStateOf(oldCity.name) }
    var editedProvince by remember (oldCity){mutableStateOf(oldCity.province)}
    Column {
        OutlinedTextField(
            value = editedName,
            onValueChange = {editedName = it},
            label = { Text("Edit City")}
        )
        OutlinedTextField(
            value = editedProvince,
            onValueChange = {editedProvince = it},
            label = { Text("Edit Province")}
        )
        Button(onClick ={
            if(editedName.isNotBlank()&&editedProvince.isNotBlank()){
                onSave(City(editedName,editedProvince))
            }
        }
        ) {
            Text("Save")
        }
    }

}

@Composable
fun CityRow(city: City,onClick:()-> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable{onClick()}
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        Text(
            text = city.name,
            fontSize = 30.sp,
            modifier = Modifier.weight(1f)
        )

        Text(
            text = city.province,
            fontSize = 30.sp,
            modifier = Modifier.weight(1f)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CityListScreenPreview() {
    ListyCity3Theme {
        CityListScreen(
            cities = listOf(
                City("Edmonton", "AB"),
                City("Vancouver", "BC"),
                City("Calgary", "AB")
            ),
            onAddCity = {},
            updateCity = { _, _ -> }
        )
    }
}