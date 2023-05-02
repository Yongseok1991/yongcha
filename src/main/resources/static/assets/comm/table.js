class Table {
    _resetVariables(id) {
        this._id = id
        this._empty = { data: null, defaultContent: '' }
        this._order = [1, 'desc']
        this._afterInit = null
        this._afterSubmit = null
        this._url = ''
        this._index = 0
        this._columns = []
        this._columnDefs = []
        this._element = $('#' + id)
        this._scroll = {}
        this._nowPage = 1
        this._cntPerPage = 10
        this._data = []
        this._params = []
        this._lengthMenu = [10, 25, 50, 100]
        this._paging = true
        this._selected = null
        this._onSelect = null
        this._margin = 1
        this._submitData = {}
        this._lastSubmitData = {}
        this._doFirstSubmit = false
        this._checkBaseVar = ''
        this._checkboxes = []
        this._ajaxConfig = {}
        this._fixedConfig = {}
        this._infoText = '검색결과'
        this._lastOrderColumn = ''
        this._wait4resize = false
        this._useLoading = false
        this._spinner = null
        this._backdrop = null
        this._resizeTimer = null
        this.listenerResetCallbacks = []
        this._hideTopDiv = false
    }

    constructor(id) {
        this._resetVariables(id)
        this._table = null

        $('.dataTables_filter .form-control').removeClass('form-control-sm');
        $('.dataTables_length .form-select').removeClass('form-select-sm').removeClass('form-control-sm');
    }

    hideTopDiv() {
        this._hideTopDiv = true
        return this
    }

    _dom() {
        let topDiv = ""

        if (!this._hideTopDiv) {
            topDiv = `<"d-flex justify-content-between mx-${this._margin} row my-1 align-items-center"`
                + '<"col-md-6 col-12"l>'
                + '<"col-md-6 col-12 dataTables_info lc-layouts text-secondary text-md-end">'
                + '>'
        }

        return topDiv
            + 'rt'
            + '<"d-flex justify-content-between mx-' + this._margin + ' row my-1 align-items-center"'
            + '<"col-md-6 col-12"i>'
            + '<"col-md-6 col-12"p>'
            + '>'
    }

    _language(options) {
        return {
            info: `${this._infoText} _TOTAL_건`,
            zeroRecords: `${this._infoText}이(가) 없습니다.`,
            infoEmpty: `${this._infoText} 0건`,
            lengthMenu: '보기 _MENU_',
            infoFiltered: '',
            paginate: {
                previous: '<span></span>',
                next: '<span></span>',
                first: '<span></span>',
                last: '<span></span>',
            },
            ...options
        }
    }

    _selection() {
        return {
            style: 'multi',
        }
    }

    _config() {
        const _this = this
        return {
            autoWidth: false,
            paging: this._paging,
            ordering: false,                // **
            dom: this._dom(),
            language: this._language(),
            select: this._selection(),
            order: [this._order],
            ...this._scroll,
            initComplete() {
                if (_this._afterInit != null)
                    _this._afterInit(_this)

                const resizeListener = () => this.resizeColumns()
                window.addEventListener('resize', resizeListener)
                _this.listenerResetCallbacks.push(() => window.removeEventListener('resize', resizeListener))
            },
        }
    }

    _initCheckboxes() {
        this._checkboxes = []

        const $box = this._element.find('.lc-check')
        const $all = $(`#${this._id}_wrapper .dataTables_scrollHead`).find('.lc-check-all')

        $all.off('click')
        if ($all.prop('checked')) $all.click()

        const _this = this

        const allClickListener = function () {
            const checked = $(this).prop('checked')
            $box.prop('checked', checked)
            _this._checkboxes = checked ? [..._this.values()] : []
        }
        const boxChangeListener = function () {
            const num = String($(this).data('num'))
            const row = _this.values().find(x => String(x[_this._checkBaseVar]) === num)
            $(this).prop('checked')
                ? _this._checkboxes.push(row)
                : _this._checkboxes = _this._checkboxes.filter(x => String(x[_this._checkBaseVar]) !== num)
            $all.prop('checked', _this._checkboxes.length > 0)
        }

        $box.on('change', boxChangeListener)
        $all.on('click', allClickListener)

        this.listenerResetCallbacks.push(() => $box.off('change', boxChangeListener))
        this.listenerResetCallbacks.push(() => $all.off('click', allClickListener))
    }

    _loading(flag) {
        if (!this._useLoading) return

        const wrapper = $(`#${this._id}_wrapper`)
        if (this._spinner == null) {
            this._spinner = $(document.createElement('div'))
            this._spinner.addClass('spinner-border')
            this._backdrop = $(document.createElement('div'))
            this._backdrop.addClass('table-backdrop')
            this._backdrop.append(this._spinner)
            wrapper.after(this._backdrop)
        }
        if (flag) {
            this._backdrop.removeClass('table-backdrop-hide')
            wrapper.addClass('table-blur')
        } else {
            this._backdrop.addClass('table-backdrop-hide')
            wrapper.removeClass('table-blur')
        }
    }

    order(index = 0, orderBy = 'desc') {
        this._order = [index, orderBy]
        return this
    }

    loading(useLoading = true) {
        this._useLoading = useLoading
        return this
    }

    infoText(text = this._infoText) {
        this._infoText = text
        return this
    }

    margin(size = 1) {
        this._margin = size
        return this
    }

    fixed(values) {
        const data = []
        const columns = []
        this._data = values
        if (Object.keys(values).length) {
            const heads = this._columns.map(col => {
                columns.push({ title: col.title })
                col.render = this._columnDefs.find(x => x.targets === col._index).render
                return col
            })
            values.map(val => {
                const tr = []
                heads.forEach(head => {
                    const v = val[head.data]
                    tr.push(head.render ? head.render(v, null, val) : v)
                })
                data.push(tr)
            })
        }
        this._url = ''
        this._ajaxConfig = {}
        this._fixedConfig = {
            data,
            columns,
            drawCallback: () => {
                if (this._table)
                    this.resizeColumns()
                else this._loading(false)
                if (this._afterSubmit != null)
                    this._afterSubmit(values, this._table)
                this._initCheckboxes()
            },
        }
        return this
    }

    get(url) {
        this._url = url
        return this
    }

    afterInit(callback) {
        this._afterInit = callback.bind(this)
        return this
    }

    afterSubmit(callback) {
        this._afterSubmit = () => {
            this._element.find('th').each((i, e) => $(e).removeClass('sorting_desc'))
            callback.bind(this)()
        }
        return this
    }

    checkbox(uniqueVarName) {
        const index = this._index++
        this._columns.push({
            ...this._empty,
            _index: index
        })
        this._checkBaseVar = uniqueVarName
        this._columnDefs.push({
            targets: index,
            orderable: false,
            render: function(data, type, full) {
                return `<div class="text-center"><input type="checkbox" class="lc-check" data-num="${full[uniqueVarName]}"></div>`
            }
        })
        return this
    }

    scroll(x = false) {
        this._scroll = {
            scrollX: x,
        }
        if (x) this._element.addClass('scroll-x')
        return this
    }

    add(column) {
        if (column instanceof Column) {
            const index = this._index++
            column.def.targets = index
            this._columns.push({
                ...column.col,
                _index: index
            })
            this._columnDefs.push(column.def)
        }
        return this
    }

    param(name, callback) {
        this._params.push({
            name,
            get: callback
        })
        return this
    }

    paging(flag = true) {
        this._paging = flag
        return this
    }

    pageOptions(array = this._lengthMenu, defaultValue = this._cntPerPage) {
        this._lengthMenu = array
        this._cntPerPage = defaultValue
        return this
    }

    selectable() {
        const _this = this
        const resetCallback = function () {
            if ($(this).find('.dataTables_empty').length) return

            if (_this._selected != null)
                _this._selected.removeClass('selected')
            _this._selected = $(this).addClass('selected')

            if (_this._onSelect) {
                const row = _this._table.row($(this))
                if (Object.keys(_this._fixedConfig).length)
                    _this._onSelect(_this._data[row.index()], row.index())
                else _this._onSelect(row.data(), row.index())
            }
        }

        this._element.on('click', 'tbody tr', resetCallback)
        this.listenerResetCallbacks.push(() => _this._element.off('click', 'tbody tr', resetCallback))
        return this
    }

    select(callback) {
        this._onSelect = callback
        return this
    }

    disableFirstSubmit() {
        this._doFirstSubmit = true
        return this
    }

    isInit() {
        return this._table !== null
    }

    destroy(draw = false) {
        if (this.isInit()) {
            this._table.clear()
            if (draw) this._table.draw()
            else this._table.destroy()
        }
    }

    reset() {
        this.listenerResetCallbacks.forEach(callback => callback())
        this.destroy()
        this._resetVariables()
    }

    init() {
        this.destroy()

        const _this = this

        const pageChangeListener = () => this.submit(null, false)
        this._element.on('page.dt', pageChangeListener)
        this.listenerResetCallbacks.push(() => _this._element.off('page.dt', pageChangeListener))

        if (this._url.length > 0) {
            const _this = this
            this._fixedConfig = {}
            this._ajaxConfig = {
                serverSide: true,
                columns: this._columns,
                columnDefs: this._columnDefs,
                deferLoading: _this._doFirstSubmit ? [0] : null,
                pageLength: _this._cntPerPage,
                lengthMenu: _this._lengthMenu,
                drawCallback: function() {
                    if (_this._table)
                        _this.resizeColumns()
                    else _this._loading(false)
                    if (_this._afterSubmit != null)
                        _this._afterSubmit(_this._data, _this._table)
                    _this._initCheckboxes()
                },
                ajax: {
                    url: this._url,
                    data: function(params) {
                        params.pageNumber = _this._nowPage
                        // params.size = _this._cntPerPage
                        params.size = params.length;

                        for (const param of _this._params)
                            params[param.name] = param.get()


                        if (_this._submitData == null && _this._lastSubmitData != null)
                            _this._submitData = _this._lastSubmitData

                        if (_this._submitData) {
                            for (const name in _this._submitData)
                                params[name] = _this._submitData[name]
                            _this._lastSubmitData = _this._submitData
                            _this._submitData = null
                        }
                    },
                    dataFilter: function(res) {
                        try {
                            const data = _this._data = JSON.parse(res).data
                            if (Array.isArray(data)) {
                                return JSON.stringify({
                                    recordsFiltered:data.number ? data.totalElements : 0,
                                    recordsTotal: data.number ? data.totalElements : 0,
                                    data
                                })
                            }

                            _this._table.page(data.number - 1)

                            return JSON.stringify({
                                recordsFiltered: data.number ? data.totalElements : 0,
                                recordsTotal: data.number ? data.totalElements : 0,
                                data: data.list ?? [],
                            })
                        } catch (e) {
                            console.error(e)
                            Util.alert('조회 오류')
                            return '[]'
                        }
                    },
                    dataSrc: function(json) {
                        return json.data
                    }
                }
            }
        }

        try {
            this._table = this._element
                .removeAttr('width')
                .DataTable({
                    ...this._config(),
                    ...this._ajaxConfig,
                    ...this._fixedConfig
                })
            if (this._checkBaseVar.length)
                $(this._table.column(0).header()).html('<input type="checkbox" class="lc-check-all">')
        } catch (e) {
            console.error(e)
        }

        return this
    }

    submit(data = null, reload = true) {
        this._loading(true)
        const info = this._table.page.info()
        if (this._paging) {
            this._nowPage = info.page + 1
            this._cntPerPage = info.length
        }
        const ajax = this._table.ajax
        if (data) this._submitData = data
        if (reload) {
            this._table.page(0)
            ajax.reload()
        }
    }

    getIndexByName(name) {
        return this._columns.find(col => col.data === name)?._index
    }

    filter(index, value) {
        this._table.column(index).search(value, false, true)
    }

    isColumnVisible(index) {
        return this._table.column(index).visible()
    }

    showColumn(index, show = true) {
        this._table.column(index).visible(show)
        this.resizeColumns()
    }

    getCheckedRows() {
        return [...this._checkboxes]
    }

    _resizeColumns() {
        this._loading(true)

        this._table.columns.adjust()
        const all = this._element.find('tr')
        const th = $(all.get(0)).find('th')
        const width = th.map((_, e) => $(e).css('width'))

        all.each((_, tr) => {
            if (_ !== 0)
                $(tr).find('td').each((i, e) =>
                    $(e).css('width', width[i]))
        })

        if (this._resizeTimer != null) {
            clearTimeout(this._resizeTimer)
            this._resizeTimer = null
        }
        this._resizeTimer = setTimeout(() => this._loading(false), 500)
    }

    resizeColumns(force = false) {
        if (!force) {
            if (this._wait4resize) return
            this._wait4resize = true

            setTimeout(() => {
                this._resizeColumns()
                this._wait4resize = false
            }, 500)
        } else {
            this._resizeColumns()
        }
    }

    values() {
        return Array.isArray(this._data)
            ? this._data
            : this._data.list ?? []
    }

    clearSelect() {
        if (this._selected != null) {
            this._selected.removeClass('selected')
            this._selected = null
        }
    }
}

class Column {
    constructor(name = '', defaultValue = '') {
        this.col = name.length > 0
            ? { data: name, defaultContent: defaultValue }
            : { data: null, defaultContent: defaultValue }

        this.def = {
            targets: -1,
        }
    }

    fixedTitle(title) {
        this.col.title = title
        return this
    }

    width(value) {
        this.def.width = value
        return this
    }

    clazz(value = '') {
        this.def.className = value
        return this
    }

    center(value = '') {
        this.def.className = "text-center"
        return this
    }

    render(callback) {
        this.def.render = function(data, type, full) {
            if (callback != null)
                return callback.bind(this)(data, full)
            return data
        }
        return this
    }

    sort(flag) {
        this.def.orderable = flag
        return this
    }
}


$('.dataTables_filter .form-control').removeClass('form-control-sm');
$('.dataTables_length .form-select').removeClass('form-select-sm').removeClass('form-control-sm');
