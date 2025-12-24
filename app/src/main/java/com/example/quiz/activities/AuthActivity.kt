package com.example.quiz.activities

import androidx.activity.ComponentActivity

class AuthActivity : ComponentActivity() {
//    private val googleSignInHelper: BasicSignInHelper by inject()
//    private val quizUserViewModel: QuizUserViewModel by viewModel()
//
//    private lateinit var navController: NavHostController
//
//    private val signUpEmail = mutableStateOf<String?>(null)
//    private val signUpUsername = mutableStateOf("")
//    private val photoUrl = mutableStateOf<String?>(null)
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//
//        val signInLauncher =
//            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
//                googleSignInHelper.handleAccountPick(
//                    this,
//                    result.resultCode,
//                    result.data,
//                    onSuccess = { email ->
//                        //handling received email
//                        //checking if user with such email exists
//                        quizUserViewModel.handleIfUserExistsByEmail(
//                            email = email,
//                            onTrue = { id ->
//                                saveUserData(id, true, this@AuthActivity)
//                                val intent = Intent(this@AuthActivity, QuizActivity::class.java)
//                                intent.putExtra("user_id", id)
//                                startActivity(intent)
//                            },
//                            onFalse = {
//                                Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show()
//                            })
//                    }, onPhotoUrl = {})
//            }
//        val signUpLauncher =
//            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
//                googleSignInHelper.handleAccountPick(
//                    this,
//                    result.resultCode,
//                    result.data,
//                    onSuccess =
//                    { email ->
//                        signUpEmail.value = email
//                    }, onPhotoUrl = { photoUrlValue -> photoUrl.value = photoUrlValue })
//            }
//        setContent {
//            navController = rememberNavController()
//
//            val coroutineScope = rememberCoroutineScope()
//
//            QuizTheme {
////                NavHost(navController = navController, startDestination = AuthRoutes.Auth.SignIn) {
////                    composable<AuthRoutes.Auth.SignIn> {
////                        SignInPage(
////                            onButtonClick = {
////                                googleSignInHelper.signIn(signInLauncher)
////                            },
////                            onRegistrationClick = {
////                                signUpEmail.value = null
////                                signUpUsername.value = ""
////                                photoUrl.value = null
////
////                                navController.navigate(AuthRoutes.Auth.SignUp)
////                            }
////                        )
////                    }
////                    composable<AuthRoutes.Auth.SignUp> {
////                        SignUpPage(
////                            usernameValue = signUpUsername.value,
////                            onUsernameChange = { newValue: String ->
////                                if (newValue.length < 21) signUpUsername.value = newValue
////                            },
////                            onAccountPick = { googleSignInHelper.signIn(signUpLauncher) },
////                            onRegistration = {
////                                if (signUpUsername.value.contains(" ")) {
////                                    Toast.makeText(
////                                        this@AuthActivity,
////                                        "Username shouldn't contain spaces",
////                                        Toast.LENGTH_SHORT
////                                    ).show()
////                                } else if (signUpUsername.value.length < 8) {
////                                    Toast.makeText(
////                                        this@AuthActivity,
////                                        "Username is too short",
////                                        Toast.LENGTH_SHORT
////                                    ).show()
////                                } else if (signUpUsername.value.length > 20) {
////                                    Toast.makeText(
////                                        this@AuthActivity,
////                                        "Username is too long",
////                                        Toast.LENGTH_SHORT
////                                    ).show()
////                                } else if (signUpEmail.value == null) {
////                                    Toast.makeText(
////                                        this@AuthActivity,
////                                        "Choose a Google account",
////                                        Toast.LENGTH_SHORT
////                                    ).show()
////                                } else {
////                                    coroutineScope.launch {
////                                        signUpEmail.value?.let { email ->
////                                            if (quizUserViewModel.findUserByEmail(email) != null) {
////                                                Toast.makeText(
////                                                    this@AuthActivity,
////                                                    "A user with this email already exists",
////                                                    Toast.LENGTH_SHORT
////                                                ).show()
////                                            } else if (quizUserViewModel.findUserByUsername(
////                                                    signUpUsername.value
////                                                ) != null
////                                            ) {
////                                                Toast.makeText(
////                                                    this@AuthActivity,
////                                                    "A user with this username already exists",
////                                                    Toast.LENGTH_SHORT
////                                                ).show()
////                                            } else {
////                                                val user = QuizUser(
////                                                    username = signUpUsername.value,
////                                                    email = email
////                                                )
////                                                when (val apiResponse =
////                                                    quizUserViewModel.createUser(user)) {
////                                                    is ApiResponse.Error -> {
////                                                        Toast.makeText(
////                                                            this@AuthActivity,
////                                                            apiResponse.message,
////                                                            Toast.LENGTH_SHORT
////                                                        ).show()
////                                                    }
////
////                                                    is ApiResponse.Success -> {
////                                                        val newUserId = apiResponse.data
////                                                        saveUserData(
////                                                            newUserId, true,
////                                                            this@AuthActivity
////                                                        )
////                                                        val intent = Intent(
////                                                            this@AuthActivity,
////                                                            QuizActivity::class.java
////                                                        )
////                                                        intent.putExtra("user_id", newUserId)
////                                                        startActivity(intent)
////                                                    }
////                                                }
////
////                                            }
////                                        }
////                                    }
////                                }
////                            },
////                            onFloatingActionButtonClick = { navController.navigateUp() },
////                            photoUrl = photoUrl.value,
////                            email = signUpEmail.value
////                        )
////                    }
////                }
//            }
//        }
//    }
}
