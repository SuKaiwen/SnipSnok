import React from "react";
import { Carousel } from "react-bootstrap";



export const HomeCarousel = () => {

    return (
        <Carousel>
            <Carousel.Item>
                <img
                    className="d-block w-100"
                    src="https://i.imgur.com/7DTE2Oo.jpg"
                    alt="First slide"
                />
                <Carousel.Caption>
                    <h3>Welcome to SnipSnok!</h3>
                    <p> </p>
                </Carousel.Caption>
            </Carousel.Item>
            <Carousel.Item>
                <img
                    className="d-block w-100"
                    src="https://i.imgur.com/R1ENqeg.jpeg"
                    alt="Second slide"
                />

                <Carousel.Caption>
                    <h3>The industry has seen gloomy days</h3>
                    <p> </p>
                </Carousel.Caption>
            </Carousel.Item>
            <Carousel.Item>
                <img
                    className="d-block w-100"
                    src="https://thumbor.forbes.com/thumbor/960x0/https%3A%2F%2Fspecials-images.forbesimg.com%2Fimageserve%2F750037840%2F960x0.jpg%3Ffit%3Dscale"
                    alt="Third slide"
                />

                <Carousel.Caption>
                    <h3>Content for everyone</h3>
                    <p></p>
                </Carousel.Caption>
            </Carousel.Item>
        </Carousel>
    );
}