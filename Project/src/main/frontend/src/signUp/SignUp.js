import React, { useState } from "react"
import "bootstrap/dist/css/bootstrap.min.css"
import { Form, Button, Spinner, Alert } from "react-bootstrap"
import { Formik } from "formik"
import * as yup from "yup"
import regex from "../common/regex"
import { checkAddress } from "../common/GoogleApiHelper"
import { sendRequest } from "../common/ApiHelper"

function SignUpForm(props) {
    // TODO delete comment - Pour Bastien, le /api est ajouté automatiquement, change signUpAPIEndpoint avec le bon endpoint
    const signUpAPIEndpoint = "/users"

    const [isSigningUp, setIsSigningUp] = useState(false)
    const [hasConnectionProblem, setHasConnectionProblem] = useState(false)
    const [hasBeenSignedUp, setHasBeenSignedUp] = useState(false)
    const [alreadyExists, setAlreadyExists] = useState(false)
    const [isValidAddress, setIsValidAddress] = useState(true)

    // TODO : remove this.... can be used for testing

    // checkAddress({
    //     address: "Rue de la boulangerie 9",
    //     npa: "1428",
    //     city: "Provence",
    //     country: "Suisse"
    // }).then( ([isValid, address])  => {
    //     if(isValid == 1){
    //         setIsValidAddress(true)
    //         console.log(address)
    //     }else{
    //         setIsValidAddress(false)
    //     }

    // })

    const attemptSignUp = values => {
        checkAddress({
            address: values.userAddress,
            npa: values.userNpa,
            city: values.userCity,
            country: values.userCountry
        }).then(([isValid, address]) => {
            if (isValid == 1) {
                setIsValidAddress(true)
                console.log(address)
            } else {
                setIsValidAddress(false)
            }
        })
        if (isValidAddress) {
            sendRequest(signUpAPIEndpoint, {
                lesDonnéesAjoutéIci: "fonction utile : JSON.stringify(data) "
            }).then(
                result => {
                    // en principe seul les requete http 200 passe ici
                    console.log("Connection ok et réponse du serveur")

                    setIsSigningUp(false)
                    setHasBeenSignedUp(true)
                },
                // Note: it's important to handle errors here
                // instead of a catch() block so that we don't swallow
                // exceptions from actual bugs in components.
                error => {
                    // en principe requete à problemes ici => code http 400 & 500 ou pas de co au serveur
                    setHasConnectionProblem(true)
                    setAlreadyExists(true)
                    setIsSigningUp(false)
                }
            )
        }
    }

    // validation rules
    const schema = yup.object({
        userEmail: yup
            .string()
            .required("Requis")
            .email(),
        userFirstname: yup
            .string()
            .required("Requis")
            .min(2)
            .max(20)
            .matches(regex.validName),
        userLastname: yup
            .string()
            .required("Requis")
            .min(2)
            .max(20)
            .matches(regex.validName),
        userPassword: yup
            .string()
            .required("Requis")
            .min(8)
            .max(32),
        userPasswordRepeat: yup
            .string()
            .required("Requis")
            .oneOf(
                [yup.ref("userPassword"), null],
                "Les mots de passe doivent être identiques"
            ),
        userAddress: yup
            .string()
            .required("Requis")
            .min(2)
            .max(50),
        userNpa: yup
            .string()
            .required("Requis")
            .min(2)
            .max(20),
        userCity: yup
            .string()
            .required("Requis")
            .min(2)
            .max(50),
        userCountry: yup
            .string()
            .required("Requis")
            .min(2)
            .max(50)
    })

    return (
        <>
            <Alert variant="success" hidden={!hasBeenSignedUp}>
                Vous êtes connecté !
            </Alert>
            <Alert variant="danger" hidden={!hasConnectionProblem}>
                hum humm.. problème de connexion au serveur
            </Alert>
            <Alert variant="warning" hidden={!alreadyExists}>
                Cette adresse e-mail est déjà utilisé
            </Alert>
            <Alert variant="warning" hidden={isValidAddress}>
                L'adresse semble incorrecte ou peu précise veuillez complétez
            </Alert>
            <Formik
                validationSchema={schema}
                onSubmit={(values, { setSubmitting }) => {
                    setIsSigningUp(!isSigningUp)
                    attemptSignUp(values)
                }}
                initialValues={{
                    userEmail: "",
                    userPassword: "",
                    userPasswordRepeat: "",
                    userFirstname: "",
                    userLastname: "",
                    userAddress: "",
                    userNpa: "",
                    userCity: "",
                    userCountry: ""
                }}
            >
                {({
                    handleSubmit,
                    handleChange,
                    touched,
                    isValid,
                    values,
                    errors
                }) => (
                    <Form noValidate onSubmit={handleSubmit}>
                        <Form.Group controlId="formEmail">
                            <Form.Label>E-mail</Form.Label>
                            <Form.Control
                                name="userEmail"
                                type="email"
                                placeholder="E-mail"
                                value={values.userEmail}
                                onChange={handleChange}
                                isValid={touched.userEmail && !errors.userEmail}
                            />
                        </Form.Group>
                        <Form.Group controlId="formFirstname">
                            <Form.Label>Prénom</Form.Label>
                            <Form.Control
                                name="userFirstname"
                                type="text"
                                placeholder="Prénom"
                                value={values.userFirstname}
                                onChange={handleChange}
                                isValid={
                                    touched.userFirstname &&
                                    !errors.userFirstname
                                }
                            />
                        </Form.Group>
                        <Form.Group controlId="formLastname">
                            <Form.Label>Nom</Form.Label>
                            <Form.Control
                                name="userLastname"
                                type="text"
                                placeholder="Nom"
                                value={values.userLastname}
                                onChange={handleChange}
                                isValid={
                                    touched.userLastname && !errors.userLastname
                                }
                            />
                        </Form.Group>

                        <Form.Group controlId="formPassword">
                            <Form.Label>Mot de passe</Form.Label>
                            <Form.Control
                                name="userPassword"
                                type="password"
                                placeholder="Mot de passe"
                                value={values.userPassword}
                                onChange={handleChange}
                                isValid={
                                    touched.userPassword && !errors.userPassword
                                }
                            />
                        </Form.Group>
                        <Form.Group controlId="formPasswordRepeat">
                            <Form.Label>
                                mot de passe encore une fois
                            </Form.Label>
                            <Form.Control
                                name="userPasswordRepeat"
                                type="password"
                                placeholder="Retappez le mot de passe"
                                value={values.userPasswordRepeat}
                                onChange={handleChange}
                                isValid={
                                    touched.userPasswordRepeat &&
                                    !errors.userPasswordRepeat
                                }
                            />
                        </Form.Group>
                        <Form.Group controlId="formAddress">
                            <Form.Label>Adresse</Form.Label>
                            <Form.Control
                                name="userAddress"
                                type="text"
                                placeholder="Adresse"
                                value={values.userAddress}
                                onChange={handleChange}
                                isValid={
                                    touched.userAddress && !errors.userAddress
                                }
                            />
                        </Form.Group>
                        <Form.Group controlId="formNpa">
                            <Form.Label>Code postal</Form.Label>
                            <Form.Control
                                name="userNpa"
                                type="text"
                                placeholder="Adresse"
                                value={values.userNpa}
                                onChange={handleChange}
                                isValid={touched.userNpa && !errors.userNpa}
                            />
                        </Form.Group>
                        <Form.Group controlId="formCity">
                            <Form.Label>Ville/village</Form.Label>
                            <Form.Control
                                name="userCity"
                                type="text"
                                placeholder="Ville"
                                value={values.userCity}
                                onChange={handleChange}
                                isValid={touched.userCity && !errors.userCity}
                            />
                        </Form.Group>
                        <Form.Group controlId="formCountry">
                            <Form.Label>Pays</Form.Label>
                            <Form.Control
                                name="userCountry"
                                type="text"
                                placeholder="Pays"
                                value={values.userCountry}
                                onChange={handleChange}
                                isValid={
                                    touched.userCountry && !errors.userCountry
                                }
                            />
                        </Form.Group>

                        <Button
                            variant="primary"
                            type="submit"
                            size="lg"
                            block
                            disabled={isSigningUp}
                        >
                            Se Connecter
                            <Spinner
                                as="span"
                                animation="border"
                                size="sm"
                                role="status"
                                aria-hidden="true"
                                hidden={!isSigningUp}
                            />
                        </Button>
                    </Form>
                )}
            </Formik>
        </>
    )
}

export default SignUpForm
