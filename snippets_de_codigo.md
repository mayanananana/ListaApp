# Fragmentos de Código Relacionados con Markdown

Aquí hay una colección de los fragmentos de código proporcionados.

---

### Snippet 1: Renderizador de Markdown en JavaScript (con `marked` y `Prism`)

```javascript
function renderMarkdown(markdown) {
    if (!markdown) {
        return '<div class="alert alert-warning">No content available</div>';
    }

    try {
        // Use marked library if available
        if (typeof marked !== 'undefined') {
            // Configure marked options
            marked.setOptions({
                breaks: true,
                gfm: true,
                headerIds: true,
                smartLists: true,
                smartypants: true,
                highlight: function(code, language) {
                    // Use Prism for syntax highlighting if available
                    if (typeof Prism !== 'undefined' && Prism.languages[language]) {
                        return Prism.highlight(code, Prism.languages[language], language);
                    }
                    return code;
                }
            });

            // Parse markdown and return HTML
            const html = marked.parse(markdown);

            // Process any special elements like image references
            const processedHtml = processSpecialMarkdown(html);

            return `<div class="markdown-content">${processedHtml}</div>`;
        } else {
            // Basic fallback if marked is not available
            console.warn('Marked library not available. Using basic formatting.');
            const basic = markdown
                .replace(/\n\n/g, '<br><br>')
                .replace(/\*\*(.*?)\*\*/g, '<strong>$1</strong>')
                .replace(/\*(.*?)\*/g, '<em>$1</em>')
                .replace(/\,(((.*?))\)/g, '<a href="$2" target="_blank">$1</a>');

            return `<div class="markdown-content">${basic}</div>`;
        }
    } catch (error) {
        console.error('Error rendering markdown:', error);
        return `<div class="alert alert-danger">Error rendering content: ${error.message}</div>`;
    }
}
```

---

### Snippet 2: Renderizador Básico de Markdown en JavaScript

```javascript
function renderMarkdown(markdown) {
        if (!markdown) return '';

        // This is a very basic markdown renderer for fallback purposes
        let html = markdown;

        // Convert headers
        html = html.replace(/^# (.*$)/gm, '<h1>$1</h1>');
        html = html.replace(/^## (.*$)/gm, '<h2>$1</h2>');
        html = html.replace(/^### (.*$)/gm, '<h3>$1</h3>');
        html = html.replace(/^#### (.*$)/gm, '<h4>$1</h4>');
        html = html.replace(/^##### (.*$)/gm, '<h5>$1</h5>');

        // Convert code blocks
        html = html.replace(/```([\s\S]*?)```/g, '<pre><code>$1</code></pre>');

        // Convert inline code
        html = html.replace(/`([^`]+)`/g, '<code>$1</code>');

        // Convert bold
        html = html.replace(/\*\*(.*?)\*\*/g, '<strong>$1</strong>');

        // Convert italic
        html = html.replace(/\*(.*?)\*/g, '<em>$1</em>');

        // Convert links
        html = html.replace(/\,(((.*?))\)/g, '<a href="$2">$1</a>');

        // Convert paragraphs - this is simplistic
        html = html.replace(/\n\s*\n/g, '</p><p>');
        html = '<p>' + html + '</p>';

        // Fix potentially broken paragraph tags
        html = html.replace(/<\/p><p><\/p><p>/g, '</p><p>');
        html = html.replace(/<\/p><p><(h[1-5])/g, '</p><$1');
        html = html.replace(/<\/(h[1-5])><p>/g, '</$1>');

        return html;
    }
```

---

### Snippet 3: Parser de Documentación Markdown en Python (Versión 1)

```python
def parse_markdown_documentation(
    content: str, asset_name: str, url: str, correlation_id: str = ''
) -> Dict[str, Any]:
    """Parse markdown documentation content for a resource.

    Args:
        content: The markdown content
        asset_name: The asset name
        url: The source URL for this documentation
        correlation_id: Identifier for tracking this request in logs

    Returns:
        Dictionary with parsed documentation details
    """
    start_time = time.time()
    logger.debug(f"[{correlation_id}] Parsing markdown documentation for '{asset_name}'")

    try:
        # Find the title (typically the first heading)
        title_match = re.search(r'^#\s+(.*?)$', content, re.MULTILINE)
        if title_match:
            title = title_match.group(1).strip()
            logger.debug(f"[{correlation_id}] Found title: '{title}'")
        else:
            title = f'AWS {asset_name}'
            logger.debug(f"[{correlation_id}] No title found, using default: '{title}'")

        # Find the main description section (all content after resource title before next heading)
        description = ''
        resource_heading_pattern = re.compile(
            rf'# Resource: {re.escape(asset_name)}\s*(.*?)(?=\n##|\Z)', re.DOTALL
        )
        resource_match = resource_heading_pattern.search(content)

        if resource_match:
            # Extract the description text and clean it up
            description = resource_match.group(1).strip()
            logger.debug(
                f"[{correlation_id}] Found resource description section: '{description[:100]}...'"
            )
        else:
            # Fall back to the description found on the starting markdown table of each github markdown page
            desc_match = re.search(r'description:\s*|-\n(.*?)\n---', content, re.MULTILINE)
            if desc_match:
                description = desc_match.group(1).strip()
                logger.debug(
                    f"[{correlation_id}] Using fallback description: '{description[:100]}...'"
                )
            else:
                description = f'Documentation for AWS {asset_name}'
                logger.debug(f'[{correlation_id}] No description found, using default')

        # Find all example snippets
        example_snippets = []

        # First try to extract from the Example Usage section
        example_section_match = re.search(r'## Example Usage\n([\s\S]*?)(?=\n## |\Z)', content)

        if example_section_match:
            # logger.debug(f"example_section_match: {example_section_match.group()}")
            example_section = example_section_match.group(1).strip()
            logger.debug(
                f'[{correlation_id}] Found Example Usage section ({len(example_section)} chars)'
            )

            # Find all subheadings in the Example Usage section with a more robust pattern
            subheading_list = list(
                re.finditer(r'### (.*?)[\n]+(.*?)(?=###|\Z)', example_section, re.DOTALL)
            )
            logger.debug(
                f'[{correlation_id}] Found {len(subheading_list)} subheadings in Example Usage section'
            )
            subheading_found = False

            # Check if there are any subheadings
            for match in subheading_list:
                # logger.info(f"subheading match: {match.group()}")
                subheading_found = True
                title = match.group(1).strip()
                subcontent = match.group(2).strip()

                logger.debug(
                    f"[{correlation_id}] Found subheading '{title}' with {len(subcontent)} chars content"
                )

                # Find code blocks in this subsection - pattern to match terraform code blocks
                code_match = re.search(r'```(?:terraform|hcl)?\s*(.*?)```', subcontent, re.DOTALL)
                if code_match:
                    code_snippet = code_match.group(1).strip()
                    example_snippets.append({'title': title, 'code': code_snippet})
                    logger.debug(
                        f"[{correlation_id}] Added example snippet for '{title}' ({len(code_snippet)} chars)"
                    )

            # If no subheadings were found, look for direct code blocks under Example Usage
            if not subheading_found:
                logger.debug(
                    f'[{correlation_id}] No subheadings found, looking for direct code blocks'
                )
                # Improved pattern for code blocks
                code_blocks = re.finditer(
                    r'```(?:terraform|hcl)?\s*(.*?)```', example_section, re.DOTALL
                )
                code_found = False

                for code_match in code_blocks:
                    code_found = True
                    code_snippet = code_match.group(1).strip()
                    example_snippets.append({'title': 'Example Usage', 'code': code_snippet})
                    logger.debug(
                        f'[{correlation_id}] Added direct example snippet ({len(code_snippet)} chars)'
                    )

                if not code_found:
                    logger.debug(
                        f'[{correlation_id}] No code blocks found in Example Usage section'
                    )
        else:
            logger.debug(f'[{correlation_id}] No Example Usage section found')

        if example_snippets:
            logger.info(f'[{correlation_id}] Found {len(example_snippets)} example snippets')
        else:
            logger.debug(f'[{correlation_id}] No example snippets found')

        # Extract Arguments Reference section
        arguments = []
        arg_ref_section_match = re.search(
            r'## Argument Reference\n([\s\S]*?)(?=\n## |\Z)', content
        )
        if arg_ref_section_match:
            arg_section = arg_ref_section_match.group(1).strip()
            logger.debug(
                f'[{correlation_id}] Found Argument Reference section ({len(arg_section)} chars)'
            )

            # Look for arguments directly under the main Argument Reference section
            args_under_main_section_match = re.search(
                r'(.*?)(?=\n###|\n##|$)', arg_section, re.DOTALL
            )
            if args_under_main_section_match:
                args_under_main_section = args_under_main_section_match.group(1).strip()
                logger.debug(
                    f'[{correlation_id}] Found arguments directly under the Argument Reference section ({len(args_under_main_section)} chars)'
                )

                # Find arguments in this subsection
                arg_matches = re.finditer(
                    r'\*\s+`([^`]+)`\s+-\s+(.*?)(?=\n\*\s+`|$)',
                    args_under_main_section,
                    re.DOTALL,
                )
                arg_list = list(arg_matches)
                logger.debug(
                    f'[{correlation_id}] Found {len(arg_list)} arguments directly under the Argument Reference section'
                )

                for match in arg_list:
                    arg_name = match.group(1).strip()
                    arg_desc = match.group(2).strip() if match.group(2) else None
                    # Do not add arguments that do not have a description
                    if arg_name is not None and arg_desc is not None:
                        arguments.append(
                            {'name': arg_name, 'description': arg_desc, 'argument_section': 'main'}
                        )
                    else:
                        logger.debug(
                            f"[{correlation_id}] Added argument '{arg_name}': '{arg_desc[:50] if arg_desc else 'No description found'}...' (truncated)"
                        )

            # Now, Find all subheadings in the Argument Reference section with a more robust pattern
            subheading_list = list(
                re.finditer(r'### (.*?)[\n]+(.*?)(?=###|\Z)', arg_section, re.DOTALL)
            )
            logger.debug(
                f'[{correlation_id}] Found {len(subheading_list)} subheadings in Argument Reference section'
            )
            subheading_found = False

            # Check if there are any subheadings
            for match in subheading_list:
                subheading_found = True
                title = match.group(1).strip()
                subcontent = match.group(2).strip()
                logger.debug(
                    f"[{correlation_id}] Found subheading '{title}' with {len(subcontent)} chars content"
                )

                # Find arguments in this subsection
                arg_matches = re.finditer(
                    r'\*\s+`([^`]+)`\s+-\s+(.*?)(?=\n\*\s+`|$)',
                    subcontent,
                    re.DOTALL,
                )
                arg_list = list(arg_matches)
                logger.debug(
                    f'[{correlation_id}] Found {len(arg_list)} arguments in subheading {title}'
                )

                for match in arg_list:
                    arg_name = match.group(1).strip()
                    arg_desc = match.group(2).strip() if match.group(2) else None
                    # Do not add arguments that do not have a description
                    if arg_name is not None and arg_desc is not None:
                        arguments.append(
                            {'name': arg_name, 'description': arg_desc, 'argument_section': title}
                        )
                    else:
                        logger.debug(
                            f"[{correlation_id}] Added argument '{arg_name}': '{arg_desc[:50] if arg_desc else 'No description found'}...' (truncated)"
                        )

            arguments = arguments if arguments else None
            if arguments:
                logger.info(
                    f'[{correlation_id}] Found {len(arguments)} arguments across all sections'
                )

        else:
            logger.debug(f'[{correlation_id}] No Argument Reference section found')

        # Extract Attributes Reference section
        attributes = []
        attr_ref_match = re.search(r'## Attribute Reference\n([\s\S]*?)(?=\n## |\Z)', content)
        if attr_ref_match:
            attr_section = attr_ref_match.group(1).strip()
            logger.debug(
                f'[{correlation_id}] Found Attribute Reference section ({len(attr_section)} chars)'
            )

            # Parse attributes - similar format to arguments
            attr_matches = re.finditer(
                r'[*-]\s+[`"]?([^`":\n]+)[`"]?(?:[`":\s-]+)?(.*?)(?=\n[*-]|\n\n|\Z)',
                attr_section,
                re.DOTALL,
            )
            attr_list = list(attr_matches)
            logger.debug(
                f'[{correlation_id}] Found {len(attr_list)} attributes in Attribute Reference section'
            )

            for match in attr_list:
                attr_name = match.group(1).strip()
                attr_desc = (
                    match.group(2).strip() if match.group(2) else 'No description available'
                )
                attributes.append({'name': attr_name, 'description': attr_desc})
                logger.debug(
                    f"[{correlation_id}] Added attribute '{attr_name}': '{attr_desc[:50]}...' (truncated)"
                )

            attributes = attributes if attributes else None
            if attributes:
                logger.info(f'[{correlation_id}] Found {len(attributes)} attributes')
        else:
            logger.debug(f'[{correlation_id}] No Attribute Reference section found')

        # Return the parsed information
        parse_time = time.time() - start_time
        logger.debug(f'[{correlation_id}] Markdown parsing completed in {parse_time:.2f} seconds')

        return {
            'title': title,
            'description': description,
            'example_snippets': example_snippets,
            'url': url,
            'arguments': arguments,
            'attributes': attributes,
        }

    except Exception as e:
        logger.exception(f'[{correlation_id}] Error parsing markdown content')
        logger.error(f'[{correlation_id}] Error type: {type(e).__name__}, message: {str(e)}')

        # Return partial info if available
        return {
            'title': f'AWS {asset_name}',
            'description': f'Documentation for AWS {asset_name} (Error parsing details: {str(e)})',
            'url': url,
            'example_snippets': None,
            'arguments': None,
            'attributes': None,
        }
```

---

### Snippet 4: Parser de Documentación Markdown en Python (Versión 2)

```python
def parse_markdown_documentation(
    content: str,
    asset_name: str,
    url: str,
    correlation_id: str = ''
) -> Dict[str, Any]:
    """Parse markdown documentation content for a resource.

    Args:
        content: The markdown content
        asset_name: The asset name
        url: The source URL for this documentation
        correlation_id: Identifier for tracking this request in logs

    Returns:
        Dictionary with parsed documentation details
    """
    start_time = time.time()
    logger.debug(f"[{correlation_id}] Parsing markdown documentation for '{asset_name}'")

    try:
        # Find the title (typically the first heading)
        title_match = re.search(r'^#\s+(.*?)$', content, re.MULTILINE)
        if title_match:
            title = title_match.group(1).strip()
            logger.debug(f"[{correlation_id}] Found title: '{title}'")
        else:
            title = f'AWS {asset_name}'
            logger.debug(f"[{correlation_id}] No title found, using default: '{title}'")

        # Find the main resource description section (all content after resource title before next heading)
        description = ''
        resource_heading_pattern = re.compile(
            rf'# {re.escape(asset_name)}\s+\(Resource\)\s*(.*?)(?=\n#|\Z)', re.DOTALL
        )
        resource_match = resource_heading_pattern.search(content)

        if resource_match:
            # Extract the description text and clean it up
            description = resource_match.group(1).strip()
            logger.debug(
                f"[{correlation_id}] Found resource description section: '{description[:100]}...'"
            )
        else:
            # Fall back to the description found on the starting markdown table of each github markdown page
            desc_match = re.search(r'description:\s*|-\n(.*?)\n---', content, re.MULTILINE)
            if desc_match:
                description = desc_match.group(1).strip()
                logger.debug(
                    f"[{correlation_id}] Using fallback description: '{description[:100]}...'"
                )
            else:
                description = f'Documentation for AWSCC {asset_name}'
                logger.debug(f'[{correlation_id}] No description found, using default')

        # Find all example snippets
        example_snippets = []

        # First try to extract from the Example Usage section
        example_section_match = re.search(r'## Example Usage\n([\s\S]*?)(?=\n## |\Z)', content)

        if example_section_match:
            # logger.debug(f"example_section_match: {example_section_match.group()}")
            example_section = example_section_match.group(1).strip()
            logger.debug(
                f'[{correlation_id}] Found Example Usage section ({len(example_section)} chars)'
            )

            # Find all subheadings in the Example Usage section with a more robust pattern
            subheading_list = list(
                re.finditer(r'### (.*?)[\n]+(.*?)(?=###|\Z)', example_section, re.DOTALL)
            )
            logger.debug(
                f'[{correlation_id}] Found {len(subheading_list)} subheadings in Example Usage section'
            )
            subheading_found = False

            # Check if there are any subheadings
            for match in subheading_list:
                # logger.info(f"subheading match: {match.group()}")
                subheading_found = True
                title = match.group(1).strip()
                subcontent = match.group(2).strip()

                logger.debug(
                    f"[{correlation_id}] Found subheading '{title}' with {len(subcontent)} chars content"
                )

                # Find code blocks in this subsection - pattern to match terraform code blocks
                code_match = re.search(r'```(?:terraform|hcl)?\s*(.*?)```', subcontent, re.DOTALL)
                if code_match:
                    code_snippet = code_match.group(1).strip()
                    example_snippets.append({'title': title, 'code': code_snippet})
                    logger.debug(
                        f"[{correlation_id}] Added example snippet for '{title}' ({len(code_snippet)} chars)"
                    )

            # If no subheadings were found, look for direct code blocks under Example Usage
            if not subheading_found:
                logger.debug(
                    f'[{correlation_id}] No subheadings found, looking for direct code blocks'
                )
                # Improved pattern for code blocks
                code_blocks = re.finditer(
                    r'```(?:terraform|hcl)?\s*(.*?)```', example_section, re.DOTALL
                )
                code_found = False

                for code_match in code_blocks:
                    code_found = True
                    code_snippet = code_match.group(1).strip()
                    example_snippets.append({'title': 'Example Usage', 'code': code_snippet})
                    logger.debug(
                        f'[{correlation_id}] Added direct example snippet ({len(code_snippet)} chars)'
                    )

                if not code_found:
                    logger.debug(
                        f'[{correlation_id}] No code blocks found in Example Usage section'
                    )
        else:
            logger.debug(f'[{correlation_id}] No Example Usage section found')

        if example_snippets:
            logger.info(f'[{correlation_id}] Found {len(example_snippets)} example snippets')
        else:
            logger.debug(f'[{correlation_id}] No example snippets found')

        # Extract Schema section
        schema_arguments = []
        schema_section_match = re.search(r'## Schema\n([\s\S]*?)(?=\n## |\Z)', content)
        if schema_section_match:
            schema_section = schema_section_match.group(1).strip()
            logger.debug(f'[{correlation_id}] Found Schema section ({len(schema_section)} chars)')

            # DO NOT Look for schema arguments directly under the main Schema section
            # args_under_main_section_match = re.search(r'(.*?)(?=\n###|\n##|$)', schema_section, re.DOTALL)
            # if args_under_main_section_match:
            #     args_under_main_section = args_under_main_section_match.group(1).strip()
            #     logger.debug(
            #         f'[{correlation_id}] Found arguments directly under the Schema section ({len(args_under_main_section)} chars)'
            #     )

            #     # Find arguments in this subsection
            #     arg_matches = re.finditer(
            #         r'-\s+`([^`]+)`\s+(.*?)(?=\n-\s+`|$)',
            #         args_under_main_section,
            #         re.DOTALL,
            #     )
            #     arg_list = list(arg_matches)
            #     logger.debug(
            #         f'[{correlation_id}] Found {len(arg_list)} arguments directly under the Argument Reference section'
            #     )

            #     for match in arg_list:
            #         arg_name = match.group(1).strip()
            #         arg_desc = match.group(2).strip() if match.group(2) else None
            #         # Do not add arguments that do not have a description
            #         if arg_name is not None and arg_desc is not None:
            #             schema_arguments.append({'name': arg_name, 'description': arg_desc, 'schema_section': "main"})
            #         logger.debug(
            #             f"[{correlation_id}] Added argument '{arg_name}': '{arg_desc[:50]}...' (truncated)"
            #         )

            # Now, Find all subheadings in the Argument Reference section with a more robust pattern
            subheading_list = list(
                re.finditer(r'### (.*?)[\n]+(.*?)(?=###|\Z)', schema_section, re.DOTALL)
            )
            logger.debug(
                f'[{correlation_id}] Found {len(subheading_list)} subheadings in Argument Reference section'
            )
            subheading_found = False

            # Check if there are any subheadings
            for match in subheading_list:
                subheading_found = True
                title = match.group(1).strip()
                subcontent = match.group(2).strip()
                logger.debug(
                    f"[{correlation_id}] Found subheading '{title}' with {len(subcontent)} chars content"
                )

                # Find arguments in this subsection
                arg_matches = re.finditer(
                    r'-\s+`([^`]+)`\s+(.*?)(?=\n-\s+`|$)',
                    subcontent,
                    re.DOTALL,
                )
                arg_list = list(arg_matches)
                logger.debug(
                    f'[{correlation_id}] Found {len(arg_list)} arguments in subheading {title}'
                )

                for match in arg_list:
                    arg_name = match.group(1).strip()
                    arg_desc = match.group(2).strip() if match.group(2) else None
                    # Do not add arguments that do not have a description
                    if arg_name is not None and arg_desc is not None:
                        schema_arguments.append(
                            {'name': arg_name, 'description': arg_desc, 'argument_section': title}
                        )
                    else:
                        logger.debug(
                            f"[{correlation_id}] Added argument '{arg_name}': '{arg_desc[:50] if arg_desc else 'No description found'}...' (truncated)"
                        )

            schema_arguments = schema_arguments if schema_arguments else None
            if schema_arguments:
                logger.info(
                    f'[{correlation_id}] Found {len(schema_arguments)} arguments across all sections'
                )
        else:
            logger.debug(f'[{correlation_id}] No Schema section found')

        # Return the parsed information
        parse_time = time.time() - start_time
        logger.debug(f'[{correlation_id}] Markdown parsing completed in {parse_time:.2f} seconds')

        return {
            'title': title,
            'description': description,
            'example_snippets': example_snippets if example_snippets else None,
            'url': url,
            'schema_arguments': schema_arguments,
        }

    except Exception as e:
        logger.exception(f'[{correlation_id}] Error parsing markdown content')
        logger.error(f'[{correlation_id}] Error type: {type(e).__name__}, message: {str(e)}')

        # Return partial info if available
        return {
            'title': f'AWSCC {asset_name}',
            'description': f'Documentation for AWSCC {asset_name} (Error parsing details: {str(e)})',
            'url': url,
            'example_snippets': None,
            'schema_arguments': None,
        }
```

---

### Snippet 5: Tests para Formateo de Markdown en Python

```python
class TestFormatMarkdown:
    """Tests for the Markdown formatting functions."""

    def test_format_markdown_case_summary(self, support_case_data):
        """Test formatting a case summary in Markdown."""
        formatted_case = format_case(support_case_data)
        markdown = format_markdown_case_summary(formatted_case)

        # Verify key elements are present in the Markdown
        assert f"**Case ID**: {support_case_data['caseId']}" in markdown
        assert f"**Subject**: {support_case_data['subject']}" in markdown
        assert "## Recent Communications" in markdown

        # Verify communication details
        first_comm = support_case_data["recentCommunications"]["communications"][0]
        assert first_comm["body"] in markdown
        assert first_comm["submittedBy"] in markdown

    def test_format_markdown_services(self, services_response_data):
        """Test formatting services in Markdown."""
        formatted_services = format_services(services_response_data["services"])
        markdown = format_markdown_services(formatted_services)

        # Verify key elements are present in the Markdown
        assert "# AWS Services" in markdown

        # Verify first service
        first_service = services_response_data["services"][0]
        assert f"## {first_service['name']}" in markdown
        assert f"`{first_service['code']}`" in markdown

        # Verify categories
        if first_service["categories"]:
            assert "### Categories" in markdown
            first_category = first_service["categories"][0]
            assert f"`{first_category['code']}`" in markdown

    def test_format_markdown_severity_levels(self, severity_levels_response_data):
        """Test formatting severity levels in Markdown."""
        formatted_levels = format_severity_levels(severity_levels_response_data["severityLevels"])
        markdown = format_markdown_severity_levels(formatted_levels)

        # Verify key elements are present in the Markdown
        assert "# AWS Support Severity Levels" in markdown

        # Verify severity levels
        for level in severity_levels_response_data["severityLevels"]:
            assert f"**{level['name']}**" in markdown
            assert f"`{level['code']}`" in markdown

    def test_format_json_response(self):
        """Test JSON response formatting."""
        test_data = {"key1": "value1", "key2": {"nested": "value2"}, "key3": [1, 2, 3]}

        formatted = format_json_response(test_data)
        assert isinstance(formatted, str)
        parsed = json.loads(formatted)
        assert parsed == test_data
```

---

### Snippet 6: Obtener Markdown desde una Base de Datos en Python

```python
def get_markdown(research_id):
    """Get markdown export for a specific research"""
    conn = get_db_connection()
    conn.row_factory = lambda cursor, row: {
        column[0]: row[idx] for idx, column in enumerate(cursor.description)
    }
    cursor = conn.cursor()
    cursor.execute(
        "SELECT * FROM research_history WHERE id = ?", (research_id,)
    )
    result = cursor.fetchone()
    conn.close()

    if not result or not result.get("report_path"):
        return jsonify({"status": "error", "message": "Report not found"}), 404

    try:
        # Resolve report path using helper function
        report_path = resolve_report_path(result["report_path"])

        with open(report_path, "r", encoding="utf-8") as f:
            content = f.read()
        return jsonify({"status": "success", "content": content})
    except Exception as e:
        return jsonify({"status": "error", "message": str(e)}), 500
```

---

### Snippet 7: Interfaz de TypeScript para un Renderizador de Markdown

```typescript
interface MarkdownRendererProps {
  content: string;
  fileExtension?: string;
  truncate?: boolean;
  maxLength?: number;
  indented?: boolean;
}
```

---

### Snippet 8: Convertidor de Texto a Markdown en Python

```python
def convert_to_markdown(text):
    """Convert extracted text to markdown format."""
    print('Converting text to markdown...')

    # Add title
    markdown = '# AWS Terraform Provider Best Practices\n\n'
    markdown += (
        '_This document was automatically extracted from the AWS Prescriptive Guidance PDF._\n\n'
    )
    markdown += f'_Source: [{PDF_URL}]({PDF_URL})_\n\n'

    # Process the text
    lines = text.split('\n')
    current_section = ''

    for line in lines:
        # Skip empty lines
        if not line.strip():
            continue

        # Try to identify headers
        if re.match(r'^[A-Z][A-Za-z\s]{2,}$', line.strip()) and len(line.strip()) < 80:
            # Looks like a header
            current_section = line.strip()

            # Stop before Document history section
            if current_section.lower() == 'document history':
                break

            markdown += f'## {current_section}\n\n'
            continue

        # Process content
        if re.match(r'^\d+\.\s+[A-Z]', line.strip()):
            # Numbered section
            markdown += f'### {line.strip()}\n\n'
        else:
            # Regular text
            # Check if it's a bullet point
            if line.strip().startswith('•') or line.strip().startswith('-'):
                markdown += f'{line.strip()}\n\n'
            else:
                markdown += f'{line.strip()}\n\n'

    # Clean up the markdown
    # Remove page numbers and headers/footers
    markdown = re.sub(r'\n\d+\n', '\n', markdown)

    # Fix bullet points
    markdown = markdown.replace('•', '*')

    # Remove excessive newlines
    markdown = re.sub(r'\n{3,}', '\n\n', markdown)

    return markdown
```

---

### Snippet 9: Descripción de un Módulo de Procesamiento de Markdown

> [Title] : Heading
> Processes Markdown content, parsing and transforming it. Centralizes interface for Markdown file handling. This directory provides the primary interface for processing Markdown content. The __init__.py module centralizes access to core functionality for parsing and transforming Markdown into other formats. The processor.py file defines a specific processor for Markdown files, responsible for reading file content and storing it within a document object. It handles basic file reading and error management, capturing raw content for subsequent processing. The core responsibility is to ingest Markdown files and prepare their content for further manipulation or conversion.
