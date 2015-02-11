import os

import pytest

from pyleus import configuration
from pyleus import exception
from pyleus.testing import mock


class TestConfiguration(object):

    @mock.patch.object(os.path, 'exists', autospec=True)
    def test__validate_config_file_not_found(
            self, mock_exists):
        mock_exists.return_value = False
        with pytest.raises(exception.ConfigurationError):
            configuration._validate_config_file("foo")
        mock_exists.assert_called_once_with("foo")

    @mock.patch.object(os.path, 'exists', autospec=True)
    @mock.patch.object(os.path, 'isfile', autospec=True)
    def test__validate_config_file_not_a_file(
            self, mock_isfile, mock_exists):
        mock_exists.return_value = True
        mock_isfile.return_value = False
        with pytest.raises(exception.ConfigurationError):
            configuration._validate_config_file("foo")
        mock_exists.assert_called_once_with("foo")
        mock_isfile.assert_called_once_with("foo")

    def test_update_configuration(self):
        default_config = configuration.DEFAULTS
        update_dict = {
            "pypi_index_url": "http://pypi-ninja.ninjacorp.com/simple"}
        updated_config = configuration.update_configuration(
            default_config, update_dict)
        assert default_config.pypi_index_url == None
        assert updated_config.pypi_index_url == \
                       "http://pypi-ninja.ninjacorp.com/simple"

    def test_update_configuration_with_plugins(self):
        default_config = configuration.DEFAULTS
        update_dict = {
            "plugins": [("a","some.Class"), ("b","some.other.Class")]
        }
        updated_config = configuration.update_configuration(
            default_config, update_dict)
        assert default_config.plugins == []
        assert updated_config.plugins == [
            ("a","some.Class"), 
            ("b","some.other.Class")
        ]

    @mock.patch.object(os.path, "exists", autospec=True)
    @mock.patch.object(os.path, "isfile", autospec=True)
    @mock.patch("pyleus.configuration.configparser.SafeConfigParser")
    def test_load_configuration_with_plugins(
            self, mock_isfile, mock_exists, MockSafeConfigParser):

        expected_plugins = [
            ("alias1", "java.class.named.Alias1"),
            ("alias2", "java.class.named.Alias2")
        ]

        def get_plugins(arg=None): 
            if arg == "plugins":
                return expected_plugins
            else:
                return []

        cfp = configuration.configparser.SafeConfigParser()
        cfp.items.side_effect = get_plugins
        config = configuration.load_configuration("")
        cfp.items.assert_called_with("plugins")
        assert config.plugins == expected_plugins
