import React, { useState } from 'react'
import 'bootstrap/dist/css/bootstrap.min.css'
import { Image, Media, Button, Container, Row, Col } from 'react-bootstrap'
import { Link } from 'react-router-dom'
import { FaUser,  FaRegClock,FaRegCalendarAlt } from 'react-icons/fa';

function LoanTable(props) {
    // TODO emprunteur -- 
    // - fetch des bonnes infos en fonction de la session utilisateur courrante
    // - afficher les bonnes infos
    // - Bouton ralonger le temps 
    // - Bouton raccourcir le temps
    // - Bouton annuler la réservation ? 

    // TODO propriétaire -- 
    // - Fetcher des bonnes infos en focntion de la session utilisateur courrante
    // - Bouton récupérer les outils plus rapidement
    // - Bouton refuser demande
    // - Bouton accepter demande

    const loan = [1,2,3,4]
    return (
        <Container fluid={true}>
            {loan.map((value, idx ) =>(
                <Media as="li" >
                  <Row className="justify-content-md-center">
                      <Col xs="12" lg="12"><h5>Marteau à tête creuse</h5></Col>
                      <Col xs="6" lg="2">
                          <Image src="tools_img_placeholder.png" rounded  fluid />
                      </Col>
                      <Col xs="6" lg="3">
                              <FaUser /> Robert le Trolleur
                              <br/>
                              <FaRegClock /> En attente 
                              <br/>
                              <FaRegCalendarAlt /> Du 02.02.2020 au 02.03.2020
                          
                      </Col>
                      <Col xs="12" lg="5">
                      
                              <p>Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source. Lorem Ipsum comes from sections 1.10.32 and 1.10.33 of "de Finibus Bonorum et Malorum" (The Extremes of Good and Evil) by Cicero, written in 45 BC. This book is a treatise on the theory of ethics, very popular during the Renaissance. The first line of Lorem Ipsum, "Lorem ipsum dolor sit amet..", comes from a line in section 1.10.32</p>
                      </Col>
                  
                      <Col xs="6" lg="2">
                          <Button variant="outline-primary" block>Afficher</Button>{' '}
                      </Col>
                  </Row>
                  <br/>
              </Media>
            ))}
          
     
        </Container>
    )
}
export default LoanTable
