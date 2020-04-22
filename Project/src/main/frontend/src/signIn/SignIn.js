import React, { useState } from 'react'
import 'bootstrap/dist/css/bootstrap.min.css'
import { Container, Form, Button, Spinner, Modal, Alert } from 'react-bootstrap'
import { Link } from 'react-router-dom'
import { Formik } from 'formik'
import * as yup from 'yup'
import { sendEzApiRequest } from '../common/ApiHelper'

const LOGIN_URI = '/authenticate'

function SignInForm(props) {
    // state var and states functions
    const [isLogging, setIsLogging] = useState(false)
    const [hasConnectionProblem, setHasConnectionProblem] = useState(false)
    const [hasBeenLoggedIn, setHasBeenLoggedIn] = useState(false)
    const [hasWrongCredential, sethasWrongCredential] = useState(false)

    const attemptLogin = (username, password) => {

        // reset error message
        setHasConnectionProblem(false)
        sethasWrongCredential(false)
        setHasBeenLoggedIn(false)

        sendEzApiRequest(LOGIN_URI, 'POST', {
            userName: username,
            password: password,
        }).then(
            result => {
                if (result.status === 403) {
                    console.log('Bad credential amigo')
                    sethasWrongCredential(true)
                } else {
                    console.log('So far so good')

                    //todo voir si à supprimer car pour l'instant inutile car le modal se ferme tout seul après connexion
                    //setHasBeenLoggedIn(true)

                    props.setLoggedUser({
                        username: result.userName,
                        admin: result.admin,
                        lastname: result.lastName,
                        firstname: result.firstName,
                    })
                
                }
                setIsLogging(false)
            },
            error => {
                console.log('Connection PAS ok', error)
                setHasConnectionProblem(true)
                setIsLogging(false)
            }
        )
    }

    // validation rules
    const schema = yup.object({
        userName: yup
            .string()
            .required('Requis')
            .min(3)
            .max(50),
        userPassword: yup.string().required('Requis'),
    })

    return (
        <>
            <Modal
                size="lg"
                show={props.showSignInForm}
                onHide={() => {
                    // hide modal and displayed messages
                    props.setShowSignInForm(false)
                    setHasConnectionProblem(false)
                    sethasWrongCredential(false)
                    //setHasBeenLoggedIn(false)
                }}
                aria-labelledby="example-modal-sizes-title-lg"
            >
                <Modal.Header closeButton>
                    <Modal.Title id="example-modal-sizes-title-lg">
                        Connexion
                    </Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Alert variant="success" hidden={!hasBeenLoggedIn}>
                        Vous êtes connecté !
                    </Alert>
                    <Alert variant="danger" hidden={!hasConnectionProblem}>
                        hum humm.. problème de connexion au serveur
                    </Alert>
                    <Alert variant="warning" hidden={!hasWrongCredential}>
                        Utilisateur inexistant
                    </Alert>
                    <Container>
                        <Formik
                            validationSchema={schema}
                            onSubmit={(values, { setSubmitting }) => {
                                setIsLogging(!isLogging)
                                attemptLogin(
                                    values.userName,
                                    values.userPassword
                                )
                            }}
                            initialValues={{ userName: '', userPassword: '' }}
                        >
                            {({
                                handleSubmit,
                                handleChange,
                                touched,
                                isValid,
                                values,
                                errors,
                            }) => (
                                <Form noValidate onSubmit={handleSubmit}>
                                    <Form.Group controlId="formBasicEmail">
                                        <Form.Label>
                                            Nom d'utilisateur
                                        </Form.Label>
                                        <Form.Control
                                            name="userName"
                                            type="text"
                                            placeholder="Nom d'utilisateur"
                                            value={values.userName}
                                            onChange={handleChange}
                                            isValid={
                                                touched.userName &&
                                                !errors.userName
                                            }
                                        />
                                    </Form.Group>

                                    <Form.Group controlId="formBasicPassword">
                                        <Form.Label>Mot de passe</Form.Label>
                                        <Form.Control
                                            name="userPassword"
                                            type="password"
                                            placeholder="Mot de passe"
                                            value={values.userPassword}
                                            onChange={handleChange}
                                            isValid={
                                                touched.userPassword &&
                                                !errors.userPassword
                                            }
                                        />
                                    </Form.Group>
                                    <Form.Group controlId="formBasicCheckbox">
                                        <Link to="/login/forget">
                                            Oops j'ai oublié mon mot de passe
                                        </Link>
                                    </Form.Group>
                                    <Button
                                        variant="primary"
                                        type="submit"
                                        size="lg"
                                        block
                                        disabled={isLogging}
                                    >
                                        Se Connecter
                                        <Spinner
                                            as="span"
                                            animation="border"
                                            size="sm"
                                            role="status"
                                            aria-hidden="true"
                                            hidden={!isLogging}
                                        />
                                    </Button>
                                </Form>
                            )}
                        </Formik>
                    </Container>
                </Modal.Body>
            </Modal>
        </>
    )
}

export default SignInForm
