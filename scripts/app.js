import { SUPPORTED_LANGS, I18N } from "./i18n.js";

(function () {
    var STORAGE_KEY = "mtt-docs-lang";
    var i18nNodes = document.querySelectorAll("[data-i18n]");
    var langButtons = document.querySelectorAll(".lang-list button[data-lang]");
    var langMenu = document.getElementById("langMenu");
    var metaDescription = document.querySelector("meta[name='description']");

    function getStoredLanguage() {
        try {
            return localStorage.getItem(STORAGE_KEY);
        } catch (error) {
            return null;
        }
    }

    function setStoredLanguage(lang) {
        try {
            localStorage.setItem(STORAGE_KEY, lang);
        } catch (error) {
            // ignore storage failures in private mode or restricted browsers
        }
    }

    function applyLanguage(lang) {
        var nextLang = SUPPORTED_LANGS.indexOf(lang) >= 0 ? lang : "en";
        var dict = I18N[nextLang] || I18N.en;

        document.documentElement.lang = nextLang;
        document.documentElement.dir = nextLang === "ar" ? "rtl" : "ltr";

        i18nNodes.forEach(function (node) {
            var key = node.getAttribute("data-i18n");
            if (dict[key]) {
                node.textContent = dict[key];
            }
        });

        if (dict.pageTitle) {
            document.title = dict.pageTitle;
        }
        if (metaDescription && dict.metaDescription) {
            metaDescription.setAttribute("content", dict.metaDescription);
        }

        langButtons.forEach(function (button) {
            button.classList.toggle("is-active", button.getAttribute("data-lang") === nextLang);
        });

        setStoredLanguage(nextLang);
    }

    langButtons.forEach(function (button) {
        button.addEventListener("click", function () {
            var lang = button.getAttribute("data-lang");
            applyLanguage(lang);
            if (langMenu && langMenu.open) {
                langMenu.open = false;
            }
        });
    });

    var yearEl = document.getElementById("year");
    if (yearEl) {
        yearEl.textContent = String(new Date().getFullYear());
    }

    var storedLang = getStoredLanguage();
    applyLanguage(storedLang || "en");
})();
