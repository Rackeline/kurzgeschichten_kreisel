const app = Vue.createApp({
    data() {
        return {
            shortstories: [],
            token: false,
            regist: false,
            home: false,
            selector: false,
            read: false,
            changeInput: false,
            genreSearch: '',
            titleSearch: '',
            shortstory: {
                id: '',
                author: '',
                creationDate: '',
                title: '',
                genre: '',
                text: ''
            },
            user: {
                username: '',
                password: '',
                role: '',
                email: ''
            },
            logindata: [],
            role: ''
        }
    },
    // created() {
    //     if(this.token == true){
    //         let config = {
    //             params: {limit: 10},
    //             headers: { Authorization: 'Bearer ' + localStorage.token}
    //       }
    //     axios
    //         .get('http://localhost:8080/shortstories/', config)
    //         .then((response) => {
    //             console.log(response.data)
    //             this.shortstories = response.data
    //         })
    //         .catch((error) => {
    //             console.log(error)
    //         })
    //     }    
    // },
    methods: {
        created() {
            let config = {
                params: { limit: 10 },
                headers: {
                    Authorization: 'Bearer ' + localStorage.token
                }
            }
            axios
                .get('http://localhost:8080/shortstories/', config)
                .then((response) => {
                    console.log(response.data)
                    this.shortstories = response.data
                })
                .catch((error) => {
                    console.log(error)
                })
        },
        showdetail(id, title, author, creationDate, genre, text) {
            this.shortstory.id = id
            this.shortstory.title = title
            this.shortstory.author = author
            this.shortstory.creationDate = creationDate
            this.shortstory.genre = genre
            this.shortstory.text = text
            console.log("selectet: ", this.shortstory.id)
            this.Read()
        },
        onPost() {
            if (this.shortstory.genre == '' || this.shortstory.text == '' || this.shortstory.title == '' || this.shortstory.author == '') {
                alert('Fehlende Pflichtfelder')
            }
            else {
                let post = {
                    title: this.shortstory.title,
                    author: this.shortstory.author,
                    genre: this.shortstory.genre,
                    creationDate: new Date(Date.now()),
                    text: this.shortstory.text
                }
                console.log(post)
                axios.post("http://localhost:8080/shortstories",
                    post)
                    .then(result => {
                        console.log(result)
                        this.statuscode = result.status
                        location.reload()
                    })
                    .catch(error =>
                        console.log(error))
            }

        },
        onChange() {
            if (this.shortstory.id == '') {
                console.log(this.shortstory.title, this.shortstory.text)
                this.onPost()
            }
            if (this.shortstory.id != '') {
                console.log('put id:', this.shortstory.id)
                let put = {
                    title: this.shortstory.title,
                    author: this.shortstory.author,
                    genre: this.shortstory.genre,
                    creationDate: new Date(Date.now()),
                    text: this.shortstory.text
                };
                axios
                    .put("http://localhost:8080/shortstories/" + this.shortstory.id, put)
                    .then((result) => {
                        alert('Änderung gespeichert.')
                        console.log(result);
                        this.Read()
                    })
            }

        },
        onDelete(id) {
            if (confirm('Möchten Sie den Eintrag endgültig löschen?')) {
                axios.delete("http://localhost:8080/shortstories/" + id).then((result) => {
                    console.log(result);
                    location.reload()
                });
            } else {
                this.Read()
            }

        },

        change() {
            this.read = false
            this.home = false
            this.changeInput = true,
                this.selector = false
        },
        Home() {
            this.titleSearch = ''
            this.created()
            this.read = false
            this.changeInput = false
            this.home = true,
            this.selector = true
            
        },
        Read() {
            this.home = false
            this.read = true
            this.changeInput = false,
                this.selector = false
        },
        newStory() {
            this.shortstory.id = ''
            this.shortstory.title = ''
            this.shortstory.author = ''
            this.shortstory.creationDate = new Date(Date.now())
            this.shortstory.genre = ''
            this.shortstory.text = ''
            this.change()
        },
        selectGenre() {
            console.log(this.genreSearch)
            if (this.genreSearch == "") {
                this.created()
            }
            else {
                let config = {
                    params: { genre: this.genreSearch },
                    headers: { Authorization: 'Bearer ' + localStorage.token }
                }
                axios
                    .get('http://localhost:8080/shortstories/', config)
                    .then((response) => {
                        console.log(response.data)
                        console.log(localStorage.token)
                        this.shortstories = response.data
                    })
                    .catch((error) => {
                        console.log(error)
                    })
            }

        },
        searchTitle() {

            if (this.titleSearch == "") {
                this.created()
            }
            else {
                let config = {
                    params: { title: this.titleSearch },
                    headers: { Authorization: 'Bearer ' + localStorage.token }
                }
                axios
                    .get('http://localhost:8080/shortstories/', config)
                    .then((response) => {
                        console.log(response.data)
                        this.titleSearch = ''
                        this.shortstories = response.data
                        if (response.data.length == 0) {
                            alert('Titel nicht gefunden.')
                            this.Home()
                        }
                    })
                    .catch((error) => {
                        console.log(error)
                    })
            }
        },
        login() {
            if (this.regist == true) {
                let postregister = {
                    username: this.user.username,
                    role: this.user.role,
                    password: this.user.password,
                    email: this.user.email
                }
                console.log(postregister)
                axios.post("http://localhost:8080/user/register",
                    postregister)
                    .then(result => {
                        console.log(result)
                        this.regist = false
                        this.statuscode = result.status
                    })
                    .catch(error =>
                        console.log(error))
            }
            if (this.regist == false) {
                let postlogin = {
                    username: this.user.username,
                    password: this.user.password
                }
                console.log(postlogin)
                axios.post("http://localhost:8080/user/login",
                    postlogin)
                    .then((response) => {
                        console.log(response.status)
                        localStorage.setItem('token', response.data.token);
                        localStorage.setItem('role', response.data.roles[0])
                        this.token = localStorage.hasOwnProperty('token')
                        this.role = localStorage.role
                        this.logindata = response.data
                        this.selector = true
                        this.home = true
                        this.created()
                        console.log(this.role)
                        console.log(localStorage.token)
                    })
                    .catch(error =>
                        console.log(error))


            }
        }

    }
})
