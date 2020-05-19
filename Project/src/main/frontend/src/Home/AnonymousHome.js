import {Col, Image, Row} from "react-bootstrap";
import {Link} from "react-router-dom";
import React from "react";

function AnonymousHome(props) {
    return <>
        <br/>
        <Row>
            <Col><h1>Bienvenue sur Easytoolz</h1></Col>
        </Row>
        <br/>
        <br/>
        <br/>
        <Row>
            <Col xs={12} sm={12} md={12} lg={6} xl={6}> <Image
                src="/home_1.jpg"
                rounded
                fluid
                style={{maxWidth: '450px'}}
            />
            </Col>
            <Col  xs={12} sm={12} md={12} lg={6} xl={6}>
                <br/>
                <h3>Vous possèdez des outils</h3>
                <br/>
                <p>

                    Vous êtes l'heureux propriétaire d'une magnifique perceuse Bosch cadencé à 1000 rpm avec ses méches incassables
                    de qualité allemande. Cependant, vous vous sentez coupable de l'utiliser que 2 fois par année.
                </p>
                <p>Pas de soucis, <Link to={"/signup"}>inscrivez-vous</Link> tout de suite sur Easytools, une plateforme parfaite pour partager ses outils.</p>
            </Col>
        </Row>
        <hr />
        <Row>
            <Col  xs={12} sm={12} md={12} lg={6} xl={6}>
                <br/>
                <h3>Vous manquez d'outil</h3>
                <br/>
                <p>Après un passage chez Ikea, vous avez dépensé vos dernière économie pour la table en verre Fäêkjö
                    mais vous vous rendez compte après coup que votre marteau n'est pas l'outil le plus adatpé pour monter votre meuble.

                </p>
                <p>
                    Qu'à cela ne tienne ! <Link to={"/signup"}>inscrivez-vous</Link> sur EasyTools pour profiter des outils des autres usagers du site
                </p>
            </Col>
            <Col  xs={12} sm={12} md={12} lg={6} xl={6}>
                <Image
                    src="/home_2.png"
                    rounded
                    fluid
                    style={{maxWidth: '450px'}}


                />
            </Col>
        </Row>

    </>
}
export default AnonymousHome