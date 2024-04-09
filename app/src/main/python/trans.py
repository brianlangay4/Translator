from deep_translator import GoogleTranslator

def auto_translate(text, target_lang):
    """
    Translate text to the specified target language using GoogleTranslator.

    Args:
    text (str): The text to translate.
    target_lang (str): The target language code (e.g., 'es' for Spanish).

    Returns:
    str: The translated text.
    """
    try:
        # Translate the text to the target language
        translated_text = GoogleTranslator(target=target_lang).translate(text)
        return translated_text
    except Exception as e:
        # Return an error message if translation fails
        return f"An error occurred during translation: {e}"

# Example usage
#text_to_translate = "Hello, world!"
#target_language = "es"  # Spanish
#translated_text = auto_translate(text_to_translate, target_language)
#print(translated_text)
    
# default return type is a list
langs_list = GoogleTranslator().get_supported_languages()  # output: [arabic, french, english etc...]

# alternatively, you can the dictionary containing languages mapped to their abbreviation
langs_dict = GoogleTranslator().get_supported_languages(as_dict=True) 
print(langs_dict)