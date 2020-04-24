import * as React from "react";
import {Card, CardDeck ,Button} from "react-bootstrap";
import {Link} from "react-router-dom";

class DashBoard extends React.Component {
    render() {
        return (
            <>
                <CardDeck>
                    <Card style={{width: '18rem'}}>
                        <Card.Img variant="top" src="/tool.png"/>
                        <Card.Body>
                            <Card.Title>Gestion de mes outils</Card.Title>
                            <Card.Text>
                                Ajouter, supprimer ou modifier un de vos outils
                            </Card.Text>
                            <Link to="/dashboard/toolList"><Button variant="primary">Consulter</Button></Link>
                        </Card.Body>
                    </Card>
                    <Card style={{width: '18rem'}}>
                        <Card.Img variant="top" src="/profil.png"/>
                        <Card.Body>
                            <Card.Title>Edition de mon profil</Card.Title>
                            <Card.Text>
                               Modifier votre profil
                            </Card.Text>
                            <Link to="/dashboard/<PROFIL>"><Button variant="primary">Consulter</Button></Link>
                        </Card.Body>
                    </Card>
                    <Card style={{width: '18rem'}}>
                        <Card.Img variant="top" src="/calendar.png"/>
                        <Card.Body>
                            <Card.Title>Gestion de mes réservations</Card.Title>
                            <Card.Text>
                               Gestion des réservations/ demandes de réservation des outils que vous avez/souhaitez emprunter.
                            </Card.Text>
                            <Link to="/dashboard/myloans/borrower"><Button variant="primary">Consulter</Button></Link>
                        </Card.Body>
                    </Card>
                    <Card style={{width: '18rem'}}>
                        <Card.Img variant="top" src="/calendar.png"/>
                        <Card.Body>
                            <Card.Title>Gestion des réservations de mes outils</Card.Title>
                            <Card.Text>
                                Gestions des réservations/ demandes de réservation de vos outils
                            </Card.Text>
                            <Link to="/dashboard/myloans/owner"><Button variant="primary">Consulter</Button></Link>
                        </Card.Body>
                    </Card>

                    </CardDeck>
            </>
        );
    }
}

export default DashBoard;