const express = require('express');
const mysql = require('mysql');
const router = express.Router()
const bodyParser = require('body-parser');
const session = require('express-session');
const crypto = require('crypto');

const app = express();
const port = 8000;

// Create a MySQL connection
const db = mysql.createConnection({
    host: '34.101.89.114',
    user: 'root',
    database: 'happyvet',
    password: 'capstone'
})

// Connect to the database
db.connect(err => {
  if (err) {
    console.error('Error connecting to database:', err);
    return;
  }
  console.log('Connected to the database');
});

// Configure middleware
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));

const generateSecretKey = () => {
  const secretLength = 32; // Length of the secret key
  return crypto.randomBytes(secretLength).toString('hex');
};

// Configure session middleware
const secretKey = generateSecretKey();
app.use(session({
  secret: secretKey,
  resave: false,
  saveUninitialized: true
}));

// Registration endpoint
router.post('/register', (req, res) => {
  const { username, password } = req.body;
  
  // Insert the user into the database
  const query = 'INSERT INTO user (email, nama, nomor, password) VALUES (?, ?, ?, ?)';
  db.query(query, [username, password], (err, result) => {
    if (err) {
      console.error('Error registering user:', err);
      res.status(500).send('Error registering user');
      return;
    }
    res.status(200).send('User registered successfully');
  });
});

// Login endpoint
router.post('/login', (req, res) => {
  const { username, password } = req.body;
  
  // Check if the user exists in the database
  const query = 'SELECT * FROM user WHERE nama = ? AND password = ?';
  db.query(query, [username, password], (err, result) => {
    if (err) {
      console.error('Error logging in:', err);
      res.status(500).send('Error logging in');
      return;
    }
    if (result.length === 0) {
      res.status(401).send('Invalid username or password');
      return;
    }
    
    // Store the user ID in the session
    req.session.userId = result[0].id;
    res.status(200).send('Login successful');
  });
});

// Protected endpoint (requires authentication)
router.get('/profile', (req, res) => {
  // Check if the user is authenticated
  if (!req.session.userId) {
    res.status(401).send('Unauthorized');
    return;
  }
  
  // Fetch the user profile from the database
  const query = 'SELECT * FROM user WHERE id = ?';
  db.query(query, [req.session.userId], (err, result) => {
    if (err) {
      console.error('Error fetching user profile:', err);
      res.status(500).send('Error fetching user profile');
      return;
    }
    if (result.length === 0) {
      res.status(404).send('User not found');
      return;
    }
    
    const userProfile = {
      id: result[0].id,
      username: result[0].username
    };
    
    res.status(200).json(userProfile);
  });
});

// Start the server
app.listen(port, () => {
    console.log(`Server listening on port ${port}`);
  });
