import React, { useState } from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import { Form, Button, Spinner, Alert } from "react-bootstrap";
import { Formik } from "formik";
import * as yup from "yup";
import regex from "../common/regex";

function SignUpForm(props) {
  const [isSigningUp, setIsSigningUp] = useState(false);
  const [hasConnectionProblem, setHasConnectionProblem] = useState(false);
  const [hasBeenSignedUp, setHasBeenSignedUp] = useState(false);
  const [alreadyExists, setAlreadyExists] = useState(false);

  const attemptSignUp = values => {
    console.log(values);
    // Todo check address in GOOGLE if return error otherwise, send info to API
    fetch("https://api.example.com/items")
      .then(res => res.json())
      .then(
        result => {
          console.log("Connection ok");
          setIsSigningUp(false);
          setHasBeenSignedUp(true);
          // TODO gestion session utilisateur
        },

        // Note: it's important to handle errors here
        // instead of a catch() block so that we don't swallow
        // exceptions from actual bugs in components.
        error => {
          // TODO check type d'erreur voir si errreur pour atteindre ou mauvais login
          setHasConnectionProblem(true);
          setAlreadyExists(true);
          setIsSigningUp(false);
        }
      );
  };

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
  });

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
      <Formik
        validationSchema={schema}
        onSubmit={(values, { setSubmitting }) => {
          setIsSigningUp(!isSigningUp);
          attemptSignUp(values);
        }}
        initialValues={{ userEmail: "", userPassword: "" }}
      >
        {({ handleSubmit, handleChange, touched, isValid, values, errors }) => (
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
                isValid={touched.userFirstname && !errors.userFirstname}
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
                isValid={touched.userLastname && !errors.userLastname}
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
                isValid={touched.userPassword && !errors.userPassword}
              />
            </Form.Group>
            <Form.Group controlId="formPasswordRepeat">
              <Form.Label>mot de passe encore une fois</Form.Label>
              <Form.Control
                name="userPasswordRepeat"
                type="password"
                placeholder="Retappez le mot de passe"
                value={values.userPasswordRepeat}
                onChange={handleChange}
                isValid={
                  touched.userPasswordRepeat && !errors.userPasswordRepeat
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
                isValid={touched.userAddress && !errors.userAddress}
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
                isValid={touched.userCountry && !errors.userCountry}
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
  );
}

export default SignUpForm;
