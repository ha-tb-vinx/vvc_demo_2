@echo off
rem =======================================
rem MSTB994-011 �w���POS�p�J�e�S���}�X�^���[�N�쐬
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MSTB994-011 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL%
