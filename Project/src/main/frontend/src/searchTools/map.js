import React, { Component } from "react"

/* eslint-disable no-undef */

class Map extends React.Component
{
    SEARCH_URI = '/objects'
    TAGS_URI = '/tags'

    state = {
        tools: [{
            name: "Marteau",
            category: "Outil non-electrique",
            lat: 63.0125156,
            lng: 14.5161565
            },
            {
            name: "Tournevis",
            category: "Outil non-electrique",
            lat: 61.0125156,
            lng: -16.5161565
        }]
    };

    constructor(props){
        super(props);

        //Utilisé pour appelé this dans handleSubmit
        //this.handleSubmit = this.handleSubmit.bind(this)
    }

    initMap() {

        var map = new google.maps.Map(document.getElementById('map'), {
        zoom: 2,
        center: new google.maps.LatLng(2.8,-187.3),
        mapTypeId: 'terrain'
    });

    var infowindow = new google.maps.InfoWindow();

        function placeMarker (tool)
        {
            var latLng = new google.maps.LatLng(tool.lat, tool.lng);
            var marker = new google.maps.Marker({
            position: latLng,
            map: map
            });

            var info = '<div class = "info"><ul><li><b>Name : </b>'
            +tool.name+'</li><li><b>Category : </b>'
            +tool.category+'</li></ul><a href='+'...'+'>'+'...'+'</a>'
            +'</div>';

            marker.addListener('click', function() {
                infowindow.close();
                infowindow.setContent(info);
                infowindow.open(map, marker);
            });
        }

        for (var i = 0; i < this.state.tools.length; i++) {
        this.state.tools.forEach(placeMarker);
        }
    }


    render()
    {
        return(
        <div id="parent">
            <div id="map">
            </div>
            <script>
                this.initMap();
            </script>
        </div>
      )
    }

    componentDidMount()
    {

    }
}

export default Map
