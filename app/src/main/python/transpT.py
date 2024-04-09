from translate import Translator

def translate_text(text, target_language='en'):
    """
    Translate text to the specified target language using py-translate.

    Args:
    text (str): The text to translate.
    target_language (str): The target language code (e.g., 'en' for English).

    Returns:
    str: The translated text.
    """
    translator = Translator(to_lang=target_language)
    try:
        translation = translator.translate(text)
        return translation
    except Exception as e:
        return f"Translation failed: {e}"

# Example usage
translated_text = translate_text("Bonjour le monde", "en")  # Translates French to English
print(translated_text)
