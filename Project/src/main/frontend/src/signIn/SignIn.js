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

    const attemptLogin = (username, password) => {
        console.log('attempt login...')
        console.log('email: ', username)
        console.log('password: ', password)


        fetch('http://127.0.0.1:8080/api/authenticate',{
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({username: username, password:password}) // body data type must match "Content-Type" header
          })
            .then(res => res.json())
            .then(
                result => {
                    if(result.status === 403){
                        console.log("Bad credential amigo")
                        sethasWrongCredential(true)
                    }else {
                        console.log("So far so good")
                        setHasBeenLoggedIn(true)
                        
                    }
                    setIsLogging(false)
                    
                    // TODO gestion session utilisateur
                },

                // Note: it's important to handle errors here
                // instead of a catch() block so that we don't swallow
                // exceptions from actual bugs in components.
                error => {
                    // TODO check type d'erreur voir si errreur pour atteindre ou mauvais login

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
            .required('Requis').min(3).max(10),
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
                                        <Form.Label>Nom d'utilisateur</Form.Label>
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
