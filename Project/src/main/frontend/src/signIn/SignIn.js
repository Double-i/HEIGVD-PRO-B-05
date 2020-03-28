import React, { useState } from 'react'
import 'bootstrap/dist/css/bootstrap.min.css'
import { Container, Form, Button, Spinner, Modal, Alert } from 'react-bootstrap'
import { Link } from 'react-router-dom'
import { Formik } from 'formik'
import * as yup from 'yup'

function SignInForm(props) {
    const [showSigInForm, setShowSigInForm] = useState(false)
    const [isLogging, setIsLogging] = useState(false)
    const [hasConnectionProblem, setHasConnectionProblem] = useState(false)
    const [hasBeenLoggedIn, setHasBeenLoggedIn] = useState(false) 
    const [hasWrongCredential, sethasWrongCredential] = useState(false) 

    const attemptLogin = (email, password) => {
        console.log('attempt login...')
        console.log('email: ', email)
        console.log('password: ', password)


        fetch('https://api.example.com/items')
            .then(res => res.json())
            .then(
                result => {
                    console.log('Connection ok')
                    setIsLogging(false)
                    setHasBeenLoggedIn(true)
                    // TODO gestion session utilisateur
                },

                // Note: it's important to handle errors here
                // instead of a catch() block so that we don't swallow
                // exceptions from actual bugs in components.
                error => {
                    // TODO check type d'erreur voir si errreur pour atteindre ou mauvais login

                    console.log('Connection PAS ok')
                    setHasConnectionProblem(true)
                    sethasWrongCredential(true)
                    setIsLogging(false)
                }
            )
    }

    // validation rules
    const schema = yup.object({
        userEmail: yup
            .string()
            .required('Requis')
            .email(),
        userPassword: yup.string().required('Requis'),
    })

    return (
        <>
            <Button onClick={() => setShowSigInForm(true)}>Se Loguers</Button>
            <Modal
                size="lg"
                show={showSigInForm}
                onHide={() => setShowSigInForm(false)}
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
                                    values.userEmail,
                                    values.userPassword
                                )
                            }}
                            initialValues={{ userEmail: '', userPassword: '' }}
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
                                        <Form.Label>E-mail</Form.Label>
                                        <Form.Control
                                            name="userEmail"
                                            type="email"
                                            placeholder="E-mail"
                                            value={values.userEmail}
                                            onChange={handleChange}
                                            isValid={
                                                touched.userEmail &&
                                                !errors.userEmail
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
                                            &#128584;
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
