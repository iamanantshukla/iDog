# iDog App

iDog is a Android application that generates random dog images using the [Dog API](https://dog.ceo/dog-api/documentation/random).

## Tech Stack

1. Kotlin - The primary programming language used for developing the iDog app.
2. Retrofit - A type-safe HTTP client for making API requests to the Dog API.
3. KOIN - A lightweight dependency injection framework used for managing dependencies in the app.
4. Jake Wharton Disk LRU Cache - A disk-based LRU cache implementation by Jake Wharton, used for storing and managing the dog images.

## Features

- Generate Dog Images
- Display a list of Dogs (at max 20 images)
- utilizes a Least Recently Used (LRU) Cache to store the generated images.

## API Endpoints

- Get Dog Image: `GET https://dog.ceo/dog-api/documentation/random`

## Setup

To run the application locally, follow these steps:

1. Clone the repository: `git clone https://github.com/iamanantshukla/iDog.git`
2. Open the project in Android Studio.
3. Build and run the application on an emulator or a physical device.

## Contributing

Contributions are welcome! If you would like to contribute to this project, please follow these steps:

1. Fork the repository.
2. Create a new branch: `git checkout -b feature/your-feature`
3. Make your changes and commit them: `git commit -m 'Add some feature'`
4. Push to the branch: `git push origin feature/your-feature`
5. Open a pull request.

Please ensure that your code follows the project's coding style and includes appropriate tests.

## License

This project is licensed under the [MIT License](LICENSE).

## Contact

If you have any questions or suggestions, feel free to reach out to me.

- GitHub: [iamanantshukla](https://github.com/iamanantshukla)

