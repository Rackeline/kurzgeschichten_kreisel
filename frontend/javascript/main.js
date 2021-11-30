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
            ownTitles: '',
            genreSearch: '',
            titleSearch: '',
            status: '',
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
                    //console.log(response.data)
                    if (this.role == "ROLE_Author" && response.data.length == 0){
                        alert("Es sind noch keine Einträge vorhanden. Schreiben Sie die erste Geschichte!")
                        this.selector = false
                        this.home = false
                        this.changeInput = true
                        
                    } 
                    if (this.role == "ROLE_Reader" && response.data.length == 0 ){
                        alert("Es sind noch keine Einträge vorhanden. Kommen Sie zu einem anderen Zeitpunkt wieder!")
                        this.logout()
                        this.selector = false
                        this.home = false
                        this.changeInput = false
                        
                    } 
                    if(response.data.length != 0) {
                        console.log(response.data)
                        this.shortstories = response.data
                        this.selector = true
                        this.home = true
                    }
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
            //console.log("selectet: ", this.shortstory.id)
            this.Read()
        },
        onPost() {
            if (this.shortstory.genre == '' || this.shortstory.text == '' || this.shortstory.title == '') {
                alert('Fehlende Pflichtfelder')
            }
            else {
                let config = {
                    headers: {
                        Authorization: 'Bearer ' + localStorage.token
                    }
                }
                let post = {
                    title: this.shortstory.title,
                    author: this.shortstory.author,
                    genre: this.shortstory.genre,
                    creationDate: new Date(Date.now()),
                    text: this.shortstory.text
                }
                //console.log(post)
                axios.post("http://localhost:8080/shortstories",
                    post, config)
                    .then(result => {
                        console.log(result)
                        this.statuscode = result.status
                        this.Home()
                    })
                    .catch(error =>
                        console.log(error))
            }

        },
        onChange() {
            if (this.shortstory.id == '') {
                //console.log(this.shortstory.title, this.shortstory.text)
                this.onPost()
            }
            if (this.shortstory.id != '') {
                let config = {
                    headers: {
                        Authorization: 'Bearer ' + localStorage.token
                    }
                }
                let put = {
                    title: this.shortstory.title,
                    author: this.shortstory.author,
                    genre: this.shortstory.genre,
                    creationDate: new Date(Date.now()),
                    text: this.shortstory.text
                };
                axios
                    .put("http://localhost:8080/shortstories/" + this.shortstory.id, put, config)
                    .then((result) => {
                        alert('Änderung gespeichert.')
                        console.log(result);
                        this.Read()
                    })
            }

        },
        onDelete(id) {
            if (confirm('Möchten Sie den Eintrag endgültig löschen?')) {
                let config = {
                    headers: {
                        Authorization: 'Bearer ' + localStorage.token
                    }
                }
                axios.delete("http://localhost:8080/shortstories/" + id, config).then((result) => {
                    console.log(result);
                    this.Home()
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
            if(this.shortstories.length == 0) {
                this.home = false,
                this.selector = false
            }
            this.titleSearch = ''
            this.ownTitles = false
            this.created()
            this.read = false
            this.changeInput = false
            
            
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
            //console.log(this.genreSearch)
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
                        //console.log(localStorage.token)
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
        showOwnTitles(){
            if(this.ownTitles == true){
                let config = {
                    params: { author: this.logindata.username },
                    headers: { Authorization: 'Bearer ' + localStorage.token }
                }
                axios
                    .get('http://localhost:8080/shortstories/', config)
                    .then((response) => {
                        console.log(response.data)
                        //console.log(localStorage.token)
                        this.shortstories = response.data
                    })
                    .catch((error) => {
                        console.log(error)
                    })
            } else {
                this.Home()
            }
        },
        login() {
            if (this.regist == true) {
                if (this.user.username == '' || this.user.role == '' || this.user.password == '' || this.user.email == '') {
                    alert('Fehlende Pflichtfelder')
                } else {
                    let postregister = {
                    username: this.user.username,
                    role: this.user.role,
                    password: this.user.password,
                    email: this.user.email
                    }
                    //console.log(postregister)
                    axios.post("http://localhost:8080/user/register",
                    postregister)
                    .then(result => {
                        console.log(result)
                        this.regist = false
                        this.statuscode = result.status
                        if(result.status == 200){
                           this.regist = false
                           alert('Willkommen ' + this.user.username + '.Sie wurden registriert.')
                           this.login()
                        }
                    })
                    .catch(error => {
                        console.log(error.response)
                        if(error.response.status == 400){
                            alert('Name/ Email existiert schon!')
                        }
                        this.user.username = ''
                        this.user.email = ''
                    })
                }  
            }
            if (this.regist == false) {
                let postlogin = {
                    username: this.user.username,
                    password: this.user.password
                }
                //console.log(postlogin)
                axios.post("http://localhost:8080/user/login",
                    postlogin)
                    .then((response) => {
                        console.log(response.status)
                        localStorage.setItem('token', response.data.token);
                        localStorage.setItem('role', response.data.roles[0])
                        this.token = localStorage.hasOwnProperty('token')
                        this.role = localStorage.role
                        this.status = response.status
                        this.logindata = response.data
                        this.created()
                        //console.log(this.role)
                        //console.log(localStorage.token)
                        
                    })
                    .catch(error => {
                        console.log(error.response)
                        if(error.response.status == 401){
                            alert('Benutzer oder Passwort unbekannt')
                        }
                        this.user.username = ''
                        this.user.password = ''
                    })
                    
            }
        },
        logout(){
            this.token = false
            this.role = ''
            this.user.username = ''
            this.user.password = ''
            this.user.role = ''
            this.user.email = ''
            window.localStorage.clear();
        }

    }
})
