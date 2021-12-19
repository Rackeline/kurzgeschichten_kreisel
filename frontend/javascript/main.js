const app = Vue.createApp({
    data() {
        return {
            shortstories: [],
            shortstoriesAuthor: [],
            token: false,
            regist: false,
            home: false,
            selector: false,
            read: false,
            changeInput: false,
            ownTitles: false,
            authorList: false,
            genreSearch: '',
            titleSearch: '',
            status: '',
            currentpage: 1,
            pages: [],
            noEntryfound: false,
            selectActive: false,
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
                headers: {
                    limit: 1000 ,
                    Authorization: 'Bearer ' + localStorage.token
                }
            }
            axios
                .get('http://localhost:8080/shortstories/', config)
                .then((response) => {
                    if (this.role == "ROLE_Author" && response.data.length == 0) {
                        alert("Es sind noch keine Einträge vorhanden. Schreiben Sie die erste Geschichte!")
                        this.selector = false
                        this.home = false
                        this.changeInput = true
                    }
                    if (this.role == "ROLE_Reader" && response.data.length == 0) {
                        alert("Es sind noch keine Einträge vorhanden. Kommen Sie zu einem anderen Zeitpunkt wieder!")
                        this.logout()
                        this.selector = false
                        this.home = false
                        this.changeInput = false
                    }
                    if (response.data.length != 0) {
                        this.pages = Math.ceil(response.data.length / 10)
                        console.log(this.pages)
                        let x = (this.currentpage - 1) * 10 
                        this.shortstories = response.data.slice(x, x+10)
                        this.selector = true
                        this.home = true
                    }
                    console.log(this.shortstories)
                })
                .catch((error) => {
                    console.log(error)
                })
        },
        showdetail(id, title, author, creationDate, genre, text, authorList) {
            this.shortstory.id = id
            this.shortstory.title = title
            this.shortstory.author = author
            this.shortstory.creationDate = creationDate
            this.shortstory.genre = genre
            this.shortstory.text = text
            this.authorList = authorList
            console.log('this.authorList', this.authorList)
            if (this.changeInput == true) {
                this.change()
                this.ownTitles = false
            }
            if (this.home == true) {
                this.Read()
            }
            if (this.ownTitles == true) {
                this.ownTitles = false
                if (this.authorList == true) {
                    this.authorList = false
                    this.OwnTitles()
                }
            }
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
        onDelete(id, authorList) {
            this.authorList = authorList
            if (confirm('Möchten Sie den Eintrag endgültig löschen?')) {
                let config = {
                    headers: {
                        Authorization: 'Bearer ' + localStorage.token
                    }
                }
                axios.delete("http://localhost:8080/shortstories/" + id, config).then((result) => {
                    console.log(result);
                    if (this.authorList == false) {
                        this.Home()
                    }
                    if (this.authorList == true) {
                        this.authorList = false
                        this.OwnTitles()
                    }
                });
            }
        },
        change() {
            this.read = false
            this.home = false
            this.ownTitles = false
            this.changeInput = true,
                this.selector = false
        },
        Home() {
            if (this.shortstories.length == 0) {
                this.home = false,
                    this.selector = false
            }
            this.titleSearch = ''
            this.ownTitles = false
            this.read = false
            this.changeInput = false
            this.selectActive = false
            this.currentpage = 1
            this.created()
        },
        ChangePage(page) {
            this.currentpage = page
            this.created()
        },
        OwnTitles() {
            this.home = false
            this.read = false
            this.changeInput = false
            this.selector = false
            this.ownTitles = !this.ownTitles
            if (this.authorList == true) {
                this.authorList = false
            }
            if (this.ownTitles == true) {
                this.showOwnTitles()
            }
            if (this.ownTitles == false) {
                this.Home()
            }
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
        search() {
            this.noEntryfound = false
            this.selectActive = true
            if (this.genreSearch == '' && this.titleSearch == '') {
                this.Home()
            } else {
                let config = {}
                if (this.titleSearch != '') {
                    config = {
                        params: { title: this.titleSearch },
                        headers: { Authorization: 'Bearer ' + localStorage.token }
                    }
                }
                if (this.genreSearch != '') {
                    config = {
                        params: { genre: this.genreSearch },
                        headers: { Authorization: 'Bearer ' + localStorage.token }
                    }
                }
                if (this.genreSearch != '' && this.titleSearch != '') {
                    config = {
                        params: { genre: this.genreSearch, title: this.titleSearch },
                        headers: { Authorization: 'Bearer ' + localStorage.token }
                    }
                }

                axios
                    .get('http://localhost:8080/shortstories/', config)
                    .then((response) => {
                        console.log(response.data)
                        this.shortstories = response.data
                        if (response.data.length == 0) {
                            this.noEntryfound = true
                        }
                    })
                    .catch((error) => {
                        console.log(error)
                    })
            }
        },
        showOwnTitles() {
            if (this.ownTitles == true) {
                let config = {
                    params: { author: this.logindata.username },
                    headers: { Authorization: 'Bearer ' + localStorage.token }
                }
                axios
                    .get('http://localhost:8080/shortstories/', config)
                    .then((response) => {
                        console.log(response.data)
                        this.shortstories = response.data
                        this.ownTitles = true
                        this.home = false
                    })
                    .catch((error) => {
                        console.log(error)
                    })
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
                    axios.post("http://localhost:8080/user/register",
                        postregister)
                        .then(result => {
                            console.log(result)
                            this.regist = false
                            this.statuscode = result.status
                            if (result.status == 200) {
                                this.regist = false
                                alert('Willkommen ' + this.user.username + '. Sie wurden registriert.')
                                this.login()
                            }
                        })
                        .catch(error => {
                            console.log(error.response)
                            this.statuscode = error.response.status
                            if (error.response.status == 400) {
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
                    })
                    .catch(error => {
                        console.log(error.response)
                        this.statuscode = error.response.status
                        if (error.response.status == 401) {
                            alert('Benutzer oder Passwort unbekannt')
                        }
                        this.user.username = ''
                        this.user.password = ''
                    })
            }
        },
        logout() {
            this.token = false
            this.home = false
            this.read = false
            this.changeInput = false
            this.role = ''
            this.ownTitles = ''
            this.user.username = ''
            this.user.password = ''
            this.user.role = ''
            this.user.email = ''
            this.currentpage = 1
            window.localStorage.clear();
        }
    }
})